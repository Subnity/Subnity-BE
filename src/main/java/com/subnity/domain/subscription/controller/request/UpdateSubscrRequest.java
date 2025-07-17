package com.subnity.domain.subscription.controller.request;

import com.subnity.common.utils.enums.SubscrCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "구독 정보 수정 요청 객체")
public record UpdateSubscrRequest(

  @Schema(description = "구독 ID")
  String subscrId,

  @Schema(description = "플랫폼 이름")
  String platformName,

  @Schema(description = "설명")
  String description,

  @Schema(description = "요금")
  String cost,

  @Schema(description = "카테고리")
  SubscrCategory category,

  @Schema(description = "알림 여부")
  Boolean isNotification
) { }
