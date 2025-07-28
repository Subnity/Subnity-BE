package com.subnity.common.mail.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.common.mail.GoogleClient;
import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.common.mail.utils.MailUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.payment_history.PaymentHistory;
import com.subnity.domain.payment_history.enums.PaymentStatus;
import com.subnity.domain.payment_history.repository.JpaPaymentHistoryRepository;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.repository.SubscrRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * MailService : 메일 관련 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MailService {

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String clientSecret;

  private final JpaPaymentHistoryRepository jpaPaymentHistoryRepository;
  private final SubscrRepository subscrRepository;
  private final GoogleClient googleClient;

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 메일을 검색하기 전에 미리 메일 토큰을 재발급
   */
  @Scheduled(cron = "00 40 23 * * *") // 23:40
  public void recreateMailAccessTokenScheduler() {
    LocalDate now = LocalDate.now();

    // 곧 돌아올 구독 목록을 조회해서 해당 회원의 메일 토큰을 재발급 (토큰의 만료 시간이 1시간이기 때문)
    List<Subscription> nextSubscrList = this.subscrRepository.findSubscrListByDate(now);
    nextSubscrList.forEach(subscr ->
      this.recreateMailAccessToken(subscr.getMember(), subscr.getMember().getMailRefreshToken())
    );
  }

  /**
   * 실제 결제가 일어난 메일을 검색하는 스케줄러
   * (스케줄러는 매일 새벽 00시 01분에 돌아감)
   */
  @Scheduled(cron = "00 01 00 * * *") // 00:01
  public void mailSearchScheduler() {
    LocalDate now = LocalDate.now();
    LocalDate yesterDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth() - 1);

    // 해당 날짜와 구독 날짜가 동일한 구독 목록을 모두 조회
    List<Subscription> subscrList = this.subscrRepository.findSubscrListByDate(yesterDate);
    subscrList.forEach(subscr -> {
      // 메일 검색 서비스 호출
      List<SearchMail> mailList = MailUtils.mailSearchByKeyword(
        SearchMailDto.builder()
          .keyword(subscr.getPlatformName())
          .cycle(subscr.getPaymentCycle())
          .build(),
        subscr.getMember(), yesterDate
      );

      // Payment History에 저장
      mailList.forEach(mail -> {
        this.jpaPaymentHistoryRepository.save(
          PaymentHistory.builder()
            .paymentStatus(PaymentStatus.SUCCESS)
            .cost(Long.parseLong(mail.getCost().replace(",", "")))
            .paymentDate(mail.getPaymentDate().toLocalDate())
            .category(subscr.getCategory())
            .subscription(subscr)
            .member(subscr.getMember())
            .build()
        );
      });
    });
  }

  /**
   * Google Access Token 재발급
   * @param member : 회원 ID
   * @param mailRefreshToken : Mail Refresh Token
   */
  public void recreateMailAccessToken(Member member, String mailRefreshToken) {
    MultiValueMap<String, String> bodyData = new LinkedMultiValueMap<>();
    bodyData.add("client_id", clientId);
    bodyData.add("client_secret", clientSecret);
    bodyData.add("refresh_token", mailRefreshToken);
    bodyData.add("grant_type", "refresh_token");

    Map<String, Object> response = googleClient.recreateAccessToken(bodyData);

    try {
      if (response.get("access_token") != null) {
        String jsonResponse = objectMapper.writeValueAsString(response);
        JsonNode json = objectMapper.readTree(jsonResponse);
        String accessToken = json.get("access_token").asText();
        System.out.println(accessToken);

        // 회원 메일 토큰 수정
        member.toBuilder()
          .mailToken(accessToken);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "Google Access Token 재발급 도중에 문제가 생겼습니다.");
    }
  }
}
