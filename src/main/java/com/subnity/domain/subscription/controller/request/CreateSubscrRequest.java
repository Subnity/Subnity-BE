package com.subnity.domain.subscription.controller.request;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.subscription.enums.PaymentCycle;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "구독 생성 요청 객체")
public record CreateSubscrRequest(

  @Schema(description = "플랫폼 이름")
  String platformName,

  @Schema(description = "설명")
  String description,

  @Schema(description = "요금")
  String cost,

  @Schema(description = "결제 주기")
  PaymentCycle paymentCycle,

  @Schema(description = "구독 상태")
  SubscrStatus status,

  @Schema(description = "구독 카테고리")
  SubscrCategory category,

  @Schema(description = "알림 여부")
  Boolean isNotification,

  @Schema(description = "해지일")
  LocalDate cancelledAt,

  @Schema(description = "결제일")
  LocalDate lastPaymentDate,

  @Schema(description = "다음 결제일")
  LocalDate nextPaymentDate
) { }
