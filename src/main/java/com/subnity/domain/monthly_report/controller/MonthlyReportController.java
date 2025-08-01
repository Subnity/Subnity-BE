package com.subnity.domain.monthly_report.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.monthly_report.controller.response.GetMonthlyReportResponse;
import com.subnity.domain.monthly_report.service.MonthlyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * MonthlyReportController : 월간 리포트 관련 Controller
 */
@RestController
@RequestMapping(value = "/monthly-report")
@RequiredArgsConstructor
@Tag(name = "Monthly Report", description = "월간 리포트 관련 API")
public class MonthlyReportController {
  private final MonthlyReportService monthlyReportService;

  /**
   * 월간 리포트 조회 엔드포인트
   * @param date : 조회하고 싶은 날짜
   * @return : 해당 날짜에 대한 지출 리포트 반환
   */
  @GetMapping(value = "")
  @Operation(summary = "월간 리포트 조회", description = "월간 리포트 조회 엔드포인트")
  public ApiResponse<GetMonthlyReportResponse> monthlyReport(@RequestParam LocalDate date) {
    return ApiResponse.onSuccess(monthlyReportService.getMonthlyReport(date));
  }
}
