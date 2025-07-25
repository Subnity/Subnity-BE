package com.subnity.common.mail.service;

import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.common.mail.utils.MailUtils;
import com.subnity.domain.payment_history.PaymentHistory;
import com.subnity.domain.payment_history.enums.PaymentStatus;
import com.subnity.domain.payment_history.repository.JpaPaymentHistoryRepository;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.repository.SubscrRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * MailService : 메일 관련 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
  private final JpaPaymentHistoryRepository jpaPaymentHistoryRepository;
  private final SubscrRepository subscrRepository;

  /**
   * 실제 결제가 일어난 메일을 검색하는 스케줄러
   * (스케줄러는 매일 새벽 00시 02분에 돌아감)
   */
  @Transactional
  @Scheduled(cron = "00 59 21 * * *") // 00:02
  public void mailSearchScheduler() {
    LocalDate now = LocalDate.now();
    // 해당 날짜와 구독 날짜가 동일한 구독 목록을 모두 조회
    List<Subscription> subscrList = this.subscrRepository.findSubscrListByDate(now);

    subscrList.forEach(subscr -> {
      // 메일 검색 서비스 호출
      List<SearchMail> mailList = MailUtils.mailSearchByKeyword(
        SearchMailDto.builder()
          .keyword(subscr.getPlatformName())
          .cycle(subscr.getPaymentCycle())
          .build(),
        subscr.getMember()
      );

      // Payment History에 저장
      mailList.forEach(mail -> {
        this.jpaPaymentHistoryRepository.save(
          PaymentHistory.builder()
            .paymentStatus(PaymentStatus.SUCCESS)
            .cost(Long.parseLong(mail.getCost().replace(",", "")))
            .paymentDate(mail.getPaymentDate())
            .category(subscr.getCategory())
            .subscription(subscr)
            .member(subscr.getMember())
            .build()
        );
      });
    });
  }
}
