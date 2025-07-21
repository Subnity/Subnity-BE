package com.subnity.domain.payment_history.controller.request;

import com.subnity.domain.payment_history.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "결제 히스토리 생성 요청 객체")
public class CreatePaymentHistoryRequest {

  @Schema(description = "결제 금액")
  private String cost;

  @Schema(description = "결제일")
  private LocalDate paymentDate;

  @Schema(description = "결제 상태")
  private PaymentStatus status;

  @Schema(description = "구독 ID")
  private long subscrId;
}
