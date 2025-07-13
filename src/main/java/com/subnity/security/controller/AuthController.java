package com.subnity.security.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.security.controller.response.CreateAccessTokenResponse;
import com.subnity.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {
  private final AuthService authService;

  /**
   * Access Token 재발급 엔드포인트
   * @param refreshToken : 쿠키에 있는 Refresh Token
   * @return : 재발급된 Access Token 객체 반환
   */
  @PostMapping(value = "/refresh")
  @Operation(summary = "Access Token 재발급", description = "Access Token 재발급 엔드포인트")
  public ApiResponse<CreateAccessTokenResponse> recreateAccessToken(
    @CookieValue(value = "RT", required = false) String refreshToken
  ) {
    return ApiResponse.onSuccess(
      CreateAccessTokenResponse.builder()
        .accessToken(authService.recreateAccessToken(refreshToken))
        .build()
    );
  }
}
