package com.subnity.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtBuilder {
  private String accessToken;
  private String refreshToken;
}
