package com.subnity.domain.monthly_report.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리별 금액 조회 Dto 객체")
public class GetCategoryCostDto {

  @Schema(description = "카테고리별 비용 ID")
  private long categoryExpenseId;

  @Schema(description = "월 리포트 ID")
  private long monthlyReportId;

  @Schema(description = "구독 카테고리")
  private SubscrCategory category;

  @Schema(description = "총 금액")
  private String totalCost;

  @Schema(description = "비율")
  private int ratio;
}
