package com.subnity.domain.payment_history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.DetailPaymentHistoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.subnity.domain.payment_history.QPaymentHistory.paymentHistory;

@Repository
@RequiredArgsConstructor
public class PaymentHistoryRepository {
  private final JPAQueryFactory queryFactory;

  @Transactional
  public void save(CreatePaymentHistoryRequest request, LocalDateTime now) {
    queryFactory.insert(paymentHistory)
      .columns(paymentHistory.cost, paymentHistory.paymentDate, paymentHistory.paymentStatus, paymentHistory.subscription.subscriptionId, paymentHistory._super.createdAt, paymentHistory._super.updatedAt)
      .values(request.getCost(), request.getPaymentDate(), request.getStatus(), request.getSubscrId(), now, now)
      .execute();
  }

  public DetailPaymentHistoryResponse paymentHistoryById(long paymentHistoryId) {
    return queryFactory.select(
      Projections.fields(
        DetailPaymentHistoryResponse.class,
        paymentHistory.paymentHistoryId,
        paymentHistory.subscription.subscriptionId.as("subscrId"),
        paymentHistory.cost,
        paymentHistory.paymentDate,
        paymentHistory.paymentStatus
      )
    )
    .from(paymentHistory)
    .where(paymentHistory.paymentHistoryId.eq(paymentHistoryId))
    .fetchOne();
  }
}
