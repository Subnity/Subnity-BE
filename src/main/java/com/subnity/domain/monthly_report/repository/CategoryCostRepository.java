package com.subnity.domain.monthly_report.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.monthly_report.CategoryCost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.subnity.domain.monthly_report.QCategoryCost.categoryCost;

@Repository
@RequiredArgsConstructor
public class CategoryCostRepository {
  private final JPAQueryFactory queryFactory;

  public List<CategoryCost> getCategoryCostByMonth(String memberId, int month) {
    return queryFactory.selectFrom(categoryCost)
      .where(
        categoryCost.monthlyReport.month.eq(month),
        categoryCost.monthlyReport.member.memberId.eq(memberId)
      )
      .fetch();
  }

  @Transactional
  public void deleteByMonthlyRepostId(long monthlyRepostId) {
    queryFactory.delete(categoryCost)
      .where(categoryCost.monthlyReport.reportId.eq(monthlyRepostId))
      .execute();
  }
}
