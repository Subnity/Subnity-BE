package com.subnity.domain.payment_history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.payment_history.PaymentHistory;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.PaymentCategoryCostDto;
import com.subnity.domain.payment_history.controller.response.PaymentTotalCostDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.subnity.domain.payment_history.QPaymentHistory.paymentHistory;

/**
 * PaymentHistoryRepository : 결제 히스토리 관련 QueryDsl Repository
 */
@Repository
@RequiredArgsConstructor
public class PaymentHistoryRepository {
  private final int[] MONTHS = { 1, 3, 5, 7, 8, 10, 12 };
  private final JPAQueryFactory queryFactory;

  /**
   * 결제 히스토리 등록
   * @param request : 결제 히스토리 생성 요청 객체
   * @param now : 요청 시간
   */
  @Transactional
  public void save(CreatePaymentHistoryRequest request, LocalDateTime now) {
    queryFactory.insert(paymentHistory)
      .columns(
        paymentHistory.cost,
        paymentHistory.paymentDate,
        paymentHistory.paymentStatus,
        paymentHistory.subscription.subscriptionId,
        paymentHistory._super.createdAt,
        paymentHistory._super.updatedAt
      )
      .values(
        request.getCost(),
        request.getPaymentDate(),
        request.getStatus(),
        request.getSubscrId(),
        now, now
      )
      .execute();
  }

  /**
   * 결제 히스토리 조회
   * @param paymentHistoryId : 결제 히스토리 ID
   * @return : 특정 결제 히스토리 반환
   */
  public PaymentHistory paymentHistoryById(long paymentHistoryId) {
    return queryFactory.selectFrom(paymentHistory)
    .where(paymentHistory.paymentHistoryId.eq(paymentHistoryId))
    .fetchOne();
  }

  /**
   * 결제 히스토리 목록 조회
   * @param memberId : 회원 ID
   * @return : 결제 히스토리 목록 반환
   */
  public List<PaymentHistory> paymentHistoryList(String memberId) {
    return queryFactory.selectFrom(paymentHistory)
    .where(paymentHistory.subscription.member.memberId.eq(memberId))
    .fetch();
  }

  /**
   * 특정 구독 결제 히스토리 목록 조회
   * @param subscrId : 구독 ID
   * @param memberId : 회원 ID
   * @return : 결제 히스토리 목록 반환
   */
  public List<PaymentHistory> paymentHistoryListBySubscrId(long subscrId, String memberId) {
    return queryFactory.selectFrom(paymentHistory)
      .where(
        paymentHistory.subscription.subscriptionId.eq(subscrId),
        paymentHistory.subscription.member.memberId.eq(memberId)
      )
      .fetch();
  }

  public PaymentTotalCostDto paymentTotalCostByMemberId(String memberId, LocalDate date) {
    LocalDate nextDate = null;
    for (int month : MONTHS) {
      if (month == date.getMonthValue()) {
        nextDate = LocalDate.of(date.getYear(), date.getMonth(), 31);
      } else {
        if (!(nextDate != null && nextDate.getDayOfMonth() == 31)) {
          nextDate = LocalDate.of(date.getYear(), date.getMonth(), 30);
        }
      }
    }

    return queryFactory.select(
      Projections.fields(
        PaymentTotalCostDto.class,
        paymentHistory.cost.sum().as("totalCost")
      )
    )
    .from(paymentHistory)
    .where(
      paymentHistory.member.memberId.eq(memberId),
      paymentHistory.paymentDate.between(
        LocalDate.of(date.getYear(), date.getMonth(), 1),
        nextDate
      )
    )
    .groupBy(paymentHistory.cost)
    .fetchOne();
  }

  public List<PaymentCategoryCostDto> paymentCategoryCostByMemberId(String memberId, LocalDate date) {
    LocalDate nextDate = null;
    for (int month : MONTHS) {
      if (month == date.getMonthValue()) {
        nextDate = LocalDate.of(date.getYear(), date.getMonth(), 31);
      } else {
        if (!(nextDate != null && nextDate.getDayOfMonth() == 31)) {
          nextDate = LocalDate.of(date.getYear(), date.getMonth(), 30);
        }
      }
    }

    return queryFactory.select(
        Projections.fields(
          PaymentCategoryCostDto.class,
          paymentHistory.cost.sum().as("totalCost"),
          paymentHistory.subscription.category.as("category")
        )
      )
      .from(paymentHistory)
      .where(
        paymentHistory.member.memberId.eq(memberId),
        paymentHistory.paymentDate.between(
          LocalDate.of(date.getYear(), date.getMonth(), 1),
          nextDate
        )
      )
      .groupBy(paymentHistory.subscription.category)
      .fetch();
  }
}
