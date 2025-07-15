package com.subnity.domain.subscription.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.subnity.domain.subscription.QSubscription.subscription;

@Repository
@RequiredArgsConstructor
public class SubscrRepository {
  private final JPAQueryFactory queryFactory;
  // 진짜 뭔가 마음에 안든다..

  public List<GetSubscrResponse> findByMemberId(String memberId) {
    return queryFactory.select(
      Projections.fields(
        GetSubscrResponse.class,
        subscription.subscriptionId,
        subscription.platformName,
        subscription.description,
        subscription.cost,
        subscription.paymentCycle,
        subscription.status,
        subscription.category,
        subscription.isNotification,
        subscription.lastPaymentDate,
        subscription.member.memberId
      )
    )
    .from(subscription)
    .where(subscription.member.memberId.eq(memberId))
    .fetch();
  }
}
