package com.subnity.common.mail.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "검색된 메일의 정보를 담고 있는 객체")
public class SearchMail {

  @Schema(description = "결제 금액")
  private String cost;

  @Schema(description = "결제일")
  private LocalDate paymentDate;
}
