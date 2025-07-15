package com.subnity.domain.subscription.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.repository.JpaSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscrService {
  private final JpaSubscriptionRepository jpaRepository;

  public void createSubscription(CreateSubscrRequest request) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    if (member != null) {
      jpaRepository.save(
        Subscription.builder()
          .platformName(request.platformName())
          .description(request.description())
          .cost(request.cost())
          .status(request.status())
          .category(request.category())
          .isNotification(request.isNotification())
          .paymentCycle(request.paymentCycle())
          .member(member)
          .cancelledAt(request.cancelledAt())
          .lastPaymentDate(request.lastPaymentDate())
          .nextPaymentDate(request.nextPaymentDate())
          .build()
      );
    } else {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독을 생성할 수 없습니다.");
    }
  }
}
