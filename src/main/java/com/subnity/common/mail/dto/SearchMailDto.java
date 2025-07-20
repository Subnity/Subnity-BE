package com.subnity.common.mail.dto;

import com.subnity.domain.subscription.enums.PaymentCycle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "메일 검색을 위한 정보 Dto 객체")
public class SearchMailDto {

  @Schema(description = "키워드")
  private String keyword;

  @Schema(description = "결제 주기")
  private PaymentCycle cycle;
}
