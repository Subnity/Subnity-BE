package com.subnity.domain.payment_history.controller.response;

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
@Schema(description = "결제 히스토리 정보 상세 조회 응답 객체")
public class DetailPaymentHistoryResponse {

  @Schema(description = "결제 히스토리 ID")
  private Long paymentHistoryId;

  @Schema(description = "요금")
  private String cost;

  @Schema(description = "결제일")
  private LocalDate paymentDate;

  @Schema(description = "결제 상태")
  private PaymentStatus paymentStatus;
}
