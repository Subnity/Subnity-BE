package com.subnity.domain.monthly_report.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.monthly_report.QMonthlyReport.monthlyReport;
import static com.subnity.domain.payment_history.QPaymentHistory.paymentHistory;

@Repository
@RequiredArgsConstructor
public class MonthlyReportRepository {
  private final JPAQueryFactory queryFactory;

  @Transactional
  public void save(String memberId) {
    queryFactory.insert(monthlyReport)
      .columns(
        monthlyReport.totalPayment,
        monthlyReport.member,
        monthlyReport.createdAt
      )
      .select(
        queryFactory.select(
          paymentHistory.cost.sum(),
          paymentHistory.member,
          Expressions.currentTimestamp()
        )
        .from(paymentHistory)
        .where(paymentHistory.member.memberId.eq(memberId))
        .groupBy(paymentHistory.category)
      )
      .execute();
  }
}
