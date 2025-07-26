package com.subnity.domain.monthly_report.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리별 금액 조회 Dto 객체")
public class GetCategoryCostDto {

  @Schema(description = "구독 카테고리")
  private SubscrCategory category;

  @Schema(description = "총 금액")
  private String totalCost;

  @Schema(description = "비율")
  private int ratio;
}
