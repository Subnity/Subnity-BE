package com.subnity.enum_api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Enum 조회 응답 객체")
public class GetEnumResponse {

  @Schema(description = "Enum 목록")
  private List<GetEnumListDto> enumList;
}
