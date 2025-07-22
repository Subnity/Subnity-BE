package com.subnity.domain.monthly_report.repository;

import com.subnity.domain.monthly_report.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
}
