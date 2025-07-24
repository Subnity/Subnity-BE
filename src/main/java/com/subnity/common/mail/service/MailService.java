package com.subnity.common.mail.service;

import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.common.mail.utils.MailUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.repository.JpaMemberRepository;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
  private final SubscrRepository subscrRepository;
  private final JpaMemberRepository jpaMemberRepository;

  /*
   주석은 추후에 지우도록!
   */
  @Scheduled(cron = "0 45 13 * * *") // 테스트 땜에..
  public void mailScheduler() {
    List<Member> memberList = this.jpaMemberRepository.findAll();
    LocalDate now = LocalDate.now();

    memberList.forEach(member -> {
      // 회원의 구독 목록 조회
      List<GetSubscrResponse> subscrList = this.subscrRepository.subscrListByMemberId(member.getMemberId());
      subscrList.forEach(subscr -> {
        // 구독 결제 날짜랑 현재 날짜와 같은지 비교
        if (subscr.getLastPaymentDate().getDayOfMonth() == now.getDayOfMonth()) { // 같다면 아래 로직 수행
          // 메일 검색 서비스 호출
          List<SearchMail> mailList = MailUtils.mailSearchByKeyword(
            SearchMailDto.builder()
              .keyword(subscr.getPlatformName())
              .cycle(subscr.getPaymentCycle())
              .build(),
            member
          );

          // 메일 존재 여부 확인
          if (!mailList.isEmpty()) { // 메일이 존재한다면!
            mailList.forEach(mail -> {
              System.out.println(mail.toString()); // 결과 -> SearchMail(cost=2,750, paymentDate=2025-07-09) (이렇게 나옴!)

              /*
               결제 확인 메일 보내주면 끝
               아래 text는 예시임. 추후에 변경 예정
               */
              String text = "안녕하세요! " + member.getName() + "님, " + subscr.getPlatformName() +
                " 서비스에서 결제가 확인되어 전해드립니다.\n" + "결제 금액은 " + mail.getCost() + "원입니다.";

              MailUtils.sendMail(member, text);
              log.info("메일을 성공적으로 전송했습니다.");
            });
          }
        }
      });
    });
  }
}
