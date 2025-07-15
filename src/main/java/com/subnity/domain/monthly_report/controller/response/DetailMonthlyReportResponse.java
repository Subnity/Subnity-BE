package com.subnity.domain.monthly_report.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "월 리포트 조회 응답 객체")
public class DetailMonthlyReportResponse {

  @Schema(description = "리포트 ID")
  private Long reportId;

  @Schema(description = "년")
  private int year;

  @Schema(description = "월")
  private String month;

  @Schema(description = "총 지출")
  private String totalPayment;

  @Schema(description = "활성화된 구독 수")
  private int activeSubscrCount;

  @Schema(description = "전월 대비")
  private String beforeContrast;

  @Builder.Default
  @Schema(description = "카테고리별 요금")
  private List<DetailCategoryExpenseResponse> categoryCosts = new ArrayList<>();
}
