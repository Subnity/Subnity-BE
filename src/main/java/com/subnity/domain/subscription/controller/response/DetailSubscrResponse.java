package com.subnity.domain.subscription.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.payment_history.controller.response.DetailPaymentHistoryResponse;
import com.subnity.domain.subscription.enums.PaymentCycle;
import com.subnity.domain.subscription_history.controller.response.DetailSubscrHistoryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "구독 정보 상세 조회 응답 객체")
public class DetailSubscrResponse {

  @Schema(description = "구독 ID")
  private Long subscriptionId;

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

  @Builder.Default
  @Schema(description = "구독 히스토리 목록")
  private List<DetailSubscrHistoryResponse> subscriptionHistoryLists = new ArrayList<>();

  @Builder.Default
  @Schema(description = "지출 히스토리 목록")
  private List<DetailPaymentHistoryResponse> paymentHistoryLists = new ArrayList<>();
}
