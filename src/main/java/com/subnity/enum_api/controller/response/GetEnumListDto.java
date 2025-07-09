package com.subnity.enum_api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Enum 목록 조회 DTO")
public class GetEnumListDto {

  @Schema(description = "enum 코드")
  private String code;

  @Schema(description = "enum 값")
  private String value;
}
