package com.subnity.auth.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.auth.controller.response.GetAccessTokenResponse;
import com.subnity.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController : Security 인증 관련 Controller
 */
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
  @Operation(summary = "Access Token 재발급", description = "Access Token 재발급 엔드포인트</br>" +
    "* 반드시 Authorization 헤더가 필요합니다.")
  public ApiResponse<GetAccessTokenResponse> recreateAccessToken(
    @CookieValue(value = "x-subnity-token", required = false) String refreshToken
  ) {
    return ApiResponse.onSuccess(
      GetAccessTokenResponse.builder()
        .accessToken(authService.recreateAccessToken(refreshToken))
        .build()
    );
  }

  /**
   * Refresh Token 제거 엔드포인트 (로그아웃)
   * @return : 공통 응답 객체 반환
   */
  @GetMapping(value = "/logout")
  @Operation(summary = "로그아웃", description = "Redis에 저장된 Refresh Token을 제거하는 엔드포인트</br>" +
    "* 쿠키나 로컬 스토리지에 있는 Access Token 및 Refresh Token은 프론트에서 제거")
  public ApiResponse<Void> logout() {
    authService.logout();
    return ApiResponse.onSuccess();
  }
}
