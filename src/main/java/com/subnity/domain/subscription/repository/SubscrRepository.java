package com.subnity.domain.subscription.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.domain.subscription.controller.request.UpdateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.subnity.domain.subscription.QSubscription.subscription;

@Repository
@RequiredArgsConstructor
public class SubscrRepository {
  private final JPAQueryFactory queryFactory;

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
        subscription.nextPaymentDate,
        subscription.member.memberId
      )
    )
    .from(subscription)
    .where(subscription.member.memberId.eq(memberId))
    .fetch();
  }

  @Transactional
  public void updateSubscription(UpdateSubscrRequest request, String memberId) {
    queryFactory.update(subscription)
      .set(subscription.platformName, request.platformName())
      .set(subscription.description, request.description())
      .set(subscription.cost, request.cost())
      .set(subscription.category, request.category())
      .set(subscription.isNotification, request.isNotification())
      .where(subscription.member.memberId.eq(memberId))
      .execute();
  }
}
