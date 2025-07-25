package com.subnity.domain.subscription.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.subscription.enums.PaymentCycle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "구독 정보 조회 응답 객체")
public class GetSubscrResponse {

  @Schema(description = "구독 ID")
  private Long subscrId;

  @Schema(description = "플랫폼 이름")
  private String platformName;

  @Schema(description = "설명")
  private String description;

  @Schema(description = "요금")
  private String cost;

  @Schema(description = "결제 주기")
  private PaymentCycle paymentCycle;

  @Schema(description = "구독 상태")
  private SubscrStatus status;

  @Schema(description = "카테고리")
  private SubscrCategory category;

  @Schema(description = "알림 여부")
  private Boolean isNotification;

  @Schema(description = "해제일")
  private LocalDate cancelledAt;

  @Schema(description = "결제일")
  private LocalDate lastPaymentDate;

  @Schema(description = "다음 결제일")
  private LocalDate nextPaymentDate;
}
