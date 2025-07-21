package com.subnity.domain.payment_history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.GetPaymentHistoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.subnity.domain.payment_history.QPaymentHistory.paymentHistory;

/**
 * PaymentHistoryRepository : 결제 히스토리 관련 QueryDsl Repository
 */
@Repository
@RequiredArgsConstructor
public class PaymentHistoryRepository {
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
  public GetPaymentHistoryResponse paymentHistoryById(long paymentHistoryId) {
    return queryFactory.select(
      Projections.fields(
        GetPaymentHistoryResponse.class,
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

  /**
   * 결제 히스토리 목록 조회
   * @param memberId : 회원 ID
   * @return : 결제 히스토리 목록 반환
   */
  public List<GetPaymentHistoryResponse> paymentHistoryList(String memberId) {
    return queryFactory.select(
      Projections.fields(
        GetPaymentHistoryResponse.class,
        paymentHistory.paymentHistoryId,
        paymentHistory.subscription.subscriptionId.as("subscrId"),
        paymentHistory.cost,
        paymentHistory.paymentDate,
        paymentHistory.paymentStatus
      )
    )
    .from(paymentHistory)
    .where(paymentHistory.subscription.member.memberId.eq(memberId))
    .fetch();
  }

  /**
   * 특정 구독 결제 히스토리 목록 조회
   * @param subscrId : 구독 ID
   * @param memberId : 회원 ID
   * @return : 결제 히스토리 목록 반환
   */
  public List<GetPaymentHistoryResponse> paymentHistoryListBySubscrId(long subscrId, String memberId) {
    return queryFactory.select(
        Projections.fields(
          GetPaymentHistoryResponse.class,
          paymentHistory.paymentHistoryId,
          paymentHistory.subscription.subscriptionId.as("subscrId"),
          paymentHistory.cost,
          paymentHistory.paymentDate,
          paymentHistory.paymentStatus
        )
      )
      .from(paymentHistory)
      .where(
        paymentHistory.subscription.subscriptionId.eq(subscrId),
        paymentHistory.subscription.member.memberId.eq(memberId)
      )
      .fetch();
  }
}
