package com.subnity.domain.payment_history.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "지출 금액 총합 조회 응답 Dto")
public class PaymentCategoryCostDto {

  @Schema(description = "지출 금액 총합")
  private long totalCost;

  @Schema(description = "구독 카테고리")
  private SubscrCategory category;
}
