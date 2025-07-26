package com.subnity.domain.monthly_report.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "월 리포트 조회 응답 객체")
public class GetMonthlyReportResponse {

  @Schema(description = "년")
  private int year;

  @Schema(description = "월")
  private int month;

  @Schema(description = "총 지출")
  private String totalPayment;

  @Builder.Default
  @Schema(description = "카테고리별 금액")
  private List<GetCategoryCostDto> categoryCostList = new ArrayList<>();
}
