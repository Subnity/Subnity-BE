package com.subnity.domain.test.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "테스트 조회 응답 객체")
public class GetTestResponse {

  @Schema(description = "ID")
  private long id;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "내용")
  private String content;
}
