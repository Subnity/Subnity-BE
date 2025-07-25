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
@Schema(description = "카테고리별 요금 조회 응답 객체")
public class GetCategoryExpenseResponse {

  @Schema(description = "카테고리별 요금 ID")
  private Long categoryExpenseId;

  @Schema(description = "카테고리")
  private SubscrCategory category;

  @Schema(description = "총 요금")
  private String totalExpense;

  @Schema(description = "비율")
  private String ratio;
}
