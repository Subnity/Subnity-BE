package com.subnity.common.mail.response;

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
@Schema(description = "메일 목록 응답 객체")
public class GmailList {

  @Schema(description = "메일 목록")
  private List<Map<String, String>> messages;
}
