package com.subnity.domain.subscription.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.controller.response.GetActiveSubscrDto;
import com.subnity.domain.subscription.controller.request.UpdateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.subnity.domain.subscription.QSubscription.subscription;

/**
 * SubscrRepository : 구독 관련 Repository
 */
@Repository
@RequiredArgsConstructor
public class SubscrRepository {
  private final JPAQueryFactory queryFactory;

  /**
   * 구독 ID로 특정 구독 조회
   * @param subscrId : 구독 ID
   * @param memberId : 회원 ID
   * @return : 특정 구독 반환
   */
  public GetSubscrResponse findBySubscrId(Long subscrId, String memberId) {
    return queryFactory.select(
        Projections.fields(
          GetSubscrResponse.class,
          subscription.subscriptionId.as("subscrId"),
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
      .where(
        subscription.member.memberId.eq(memberId),
        subscription.subscriptionId.eq(subscrId)
      )
      .fetchOne();
  }

  /**
   * 구독 목록 조회
   * @param memberId : 회원 ID
   * @return : 구독 목록 반환
   */
  public List<GetSubscrResponse> subscrListByMemberId(String memberId) {
    return queryFactory.select(
      Projections.fields(
        GetSubscrResponse.class,
        subscription.subscriptionId.as("subscrId"),
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

  /**
   * 구독 정보 수정
   * @param request : 구독 수정 요청 객체
   * @param memberId : 회원 ID
   */
  @Transactional
  public void updateSubscr(UpdateSubscrRequest request, String memberId) {
    queryFactory.update(subscription)
      .set(subscription.platformName, request.platformName())
      .set(subscription.description, request.description())
      .set(subscription.cost, Long.parseLong(request.cost().replace(",", "")))
      .set(subscription.category, request.category())
      .set(subscription.isNotification, request.isNotification())
      .where(
        subscription.member.memberId.eq(memberId),
        subscription.subscriptionId.eq(Long.parseLong(request.subscrId()))
      )
      .execute();
  }

  /**
   * 구독중인 구독 목록 조회
   * @return : 활성화된 구독 조회 Dto
   */
  public List<GetActiveSubscrDto> activeTotalSubscrCount() {
    return queryFactory.select(
        Projections.fields(
          GetActiveSubscrDto.class,
          subscription.subscriptionId.as("subscrId"),
          subscription.platformName,
          subscription.cost,
          subscription.status,
          subscription.category
        )
      )
      .from(subscription)
      .where(subscription.status.eq(SubscrStatus.ACTIVITY))
      .fetch();
  }

  public List<Subscription> findSubscrListByDate(LocalDate date) {
    return queryFactory.selectFrom(subscription)
      .where(subscription.nextPaymentDate.eq(date))
      .fetch();
  }
}
