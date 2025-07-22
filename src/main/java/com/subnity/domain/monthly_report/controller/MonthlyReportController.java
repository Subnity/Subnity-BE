package com.subnity.domain.monthly_report.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.monthly_report.service.MonthlyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monthly-report")
@RequiredArgsConstructor
@Tag(name = "Monthly Report", description = "월 리포트 관련 API")
public class MonthlyReportController {
  private final MonthlyReportService monthlyReportService;

  @GetMapping(value = "/create")
  @Operation(summary = "월간 구독 리포트 생성", description = "월간 구독 리포트 생성 엔드포인트")
  public ApiResponse<Void> createMonthlyReport() {
    monthlyReportService.createMonthlyReport();
    return ApiResponse.onSuccess();
  }
}
