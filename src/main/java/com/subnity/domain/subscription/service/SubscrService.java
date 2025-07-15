package com.subnity.domain.subscription.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import com.subnity.domain.subscription.repository.JpaSubscrRepository;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscrService {
  private final JpaSubscrRepository jpaRepository;
  private final SubscrRepository subscrRepository;

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
          .lastPaymentDate(request.lastPaymentDate())
          .member(member)
          .build()
      );
    } else {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독을 생성할 수 없습니다.");
    }
  }

  public List<GetSubscrResponse> getSubscrList() {
    String memberId = SecurityUtils.getAuthMemberId();
    return subscrRepository.findByMemberId(memberId);
  }
}
