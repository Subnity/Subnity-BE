package com.subnity.auth.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Access Token 재발급 응답 객체")
public class CreateAccessTokenResponse {

  @Schema(description = "Access Token")
  private String accessToken;
}
