package com.subnity.domain.subscription.controller.response;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "활성화된 구독 조회 Dto 객체")
public class GetActiveSubscrDto {

  @Schema(description = "구독 ID")
  private long subscrId;

  @Schema(description = "플랫폼 이름")
  private String platformName;

  @Schema(description = "금액")
  private String cost;

  @Schema(description = "구독 상태")
  private SubscrStatus status;

  @Schema(description = "구독 카테고리")
  private SubscrCategory category;

}
