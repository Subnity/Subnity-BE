package com.subnity.common.mail.response;

import com.subnity.common.mail.dto.PayloadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "특정 메일의 정보 응답 객체")
public class Gmail {

  @Schema(description = "메일 페이로드")
  private PayloadDto payload;
}
