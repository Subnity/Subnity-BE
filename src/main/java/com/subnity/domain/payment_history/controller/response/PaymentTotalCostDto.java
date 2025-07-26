package com.subnity.domain.payment_history.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "지출 금액 총합 조회 응답 Dto")
public class PaymentTotalCostDto {

  @Schema(description = "지출 금액 총합")
  private long totalCost;
}
