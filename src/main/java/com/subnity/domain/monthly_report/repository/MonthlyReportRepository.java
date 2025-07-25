package com.subnity.domain.monthly_report.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.monthly_report.MonthlyReport;
import com.subnity.domain.monthly_report.controller.response.GetMonthlyReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.monthly_report.QMonthlyReport.monthlyReport;

@Repository
@RequiredArgsConstructor
public class MonthlyReportRepository {
  private final JPAQueryFactory queryFactory;

  /**
   * 해당 월의 리포트 조회
   * @param memberId : 회원 ID
   * @param month : 달
   * @return : 해당 월의 리포트 반환
   */
  public MonthlyReport getMonthlyReportByMonth(String memberId, int month) {
    return queryFactory.selectFrom(monthlyReport)
    .where(
      monthlyReport.month.eq(month),
      monthlyReport.member.memberId.eq(memberId)
    )
    .fetchOne();
  }
}
