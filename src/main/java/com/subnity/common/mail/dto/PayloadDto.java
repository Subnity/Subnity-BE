package com.subnity.common.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Gmail API 페이로드 Dto 객체")
public class PayloadDto {

  @Schema(description = "Header 목록")
  private List<Map<String, Object>> headers;

  @Schema(description = "Body")
  private Map<String, Object> body;
}
