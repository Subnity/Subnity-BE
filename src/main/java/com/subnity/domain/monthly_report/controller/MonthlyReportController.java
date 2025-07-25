package com.subnity.domain.monthly_report.controller;

import com.subnity.domain.monthly_report.service.MonthlyReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
@RequiredArgsConstructor
@Tag(name = "Monthly Report", description = "월간 리포트 관련 API")
public class MonthlyReportController {
  private final MonthlyReportService monthlyReportService;

  // 월간 리포트 생성 엔드포인트

  // 월간 리포트 조회 엔드포인트

  // 월간 리포트 목록 조회 엔드포인트
}
