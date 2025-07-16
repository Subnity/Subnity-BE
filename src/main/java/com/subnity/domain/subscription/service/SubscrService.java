package com.subnity.domain.subscription.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import com.subnity.domain.subscription.enums.PaymentCycle;
import com.subnity.domain.subscription.repository.JpaSubscrRepository;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 코드 정리 및 리펙토링 필요성 있음
 */
@Service
@RequiredArgsConstructor
public class SubscrService {
  private final int[] MONTHS = { 1, 3, 5, 7, 8, 10, 12 }; // 31일이 있는 달

  private final JpaSubscrRepository jpaRepository;
  private final SubscrRepository subscrRepository;

  public void createSubscription(CreateSubscrRequest request) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    if (member != null) {
      this.jpaRepository.save(
        Subscription.builder()
          .platformName(request.platformName())
          .description(request.description())
          .cost(request.cost())
          .status(request.status())
          .category(request.category())
          .isNotification(request.isNotification())
          .paymentCycle(request.paymentCycle())
          .lastPaymentDate(request.lastPaymentDate())
          .nextPaymentDate(this.getNextPaymentDate(request.lastPaymentDate(), request.paymentCycle()))
          .member(member)
          .build()
      );
    } else {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독을 생성할 수 없습니다.");
    }
  }

  public List<GetSubscrResponse> getSubscrList() {
    String memberId = SecurityUtils.getAuthMemberId();
    return this.subscrRepository.findByMemberId(memberId);
  }

  private LocalDate getNextPaymentDate(LocalDate date, PaymentCycle paymentCycle) {
    LocalDate nextPaymentDate = null;

    switch (paymentCycle) {
      case MONTH:
        String lastMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        for (int m : MONTHS) {
          if (lastMonth.contains(String.valueOf(m))) {
            nextPaymentDate = date.plusDays(31);
          } else {
            nextPaymentDate = date.plusDays(30);
          }
        }
        break;
      case YEAR:
        nextPaymentDate = date.plusYears(1);
        break;
    }

    return nextPaymentDate;
  }
}
