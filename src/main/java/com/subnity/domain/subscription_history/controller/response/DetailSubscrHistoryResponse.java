package com.subnity.domain.subscription_history.controller.response;

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
@Schema(description = "구독 히스토리 정보 조회 응답 객체")
public class DetailSubscrHistoryResponse {

  @Schema(description = "구독 히스토리 ID")
  private Long subscriptionHistoryId;

  @Schema(description = "해지일")
  private LocalDate cancelDate;
}
