package com.subnity.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtBuilder {
  private String accessToken;
  private String refreshToken;
}
