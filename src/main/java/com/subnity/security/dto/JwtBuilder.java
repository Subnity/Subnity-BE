package com.subnity.security.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtBuilder {
  private String accessToken;
  private String refreshToken;
}
