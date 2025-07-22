package com.subnity.domain.monthly_report.controller;

import com.subnity.domain.monthly_report.service.MonthlyReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monthly-report")
@RequiredArgsConstructor
@Tag(name = "Monthly Report", description = "월 리포트 관련 API")
public class MonthlyReportController {
  private final MonthlyReportService monthlyReportService;

}
