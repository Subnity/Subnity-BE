package com.subnity.auth.service;

import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.enums.Role;
import com.subnity.auth.dto.JwtBuilder;
import com.subnity.auth.dto.JwtClaimsDto;
import com.subnity.auth.utils.JwtUtils;
import com.subnity.auth.utils.RedisUtils;
import org.springframework.stereotype.Service;

/**
 * AuthService : 인증 관련 서비스 클래스
 */
@Service
public class AuthService {

  /**
   * Access Token 재발급 메서드
   * @param token : 쿠키에 있는 Refresh Token
   * @return : 재발급된 Access Token 반환 | 예외 발생시 400 코드 반환
   */
  public String recreateAccessToken(String token) {
    String refreshToken = RedisUtils.get(token);

    if (token != null && refreshToken != null && !token.equals(refreshToken)) {
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "잘못된 토큰입니다.");
    }

    if (refreshToken != null && JwtUtils.getValidateToken(refreshToken)) {
      String memberId = JwtUtils.getMemberId(refreshToken);
      String role = JwtUtils.getRole(refreshToken);

      JwtBuilder jwtDto = JwtUtils.createToken(
        JwtClaimsDto.builder()
          .memberId(memberId)
          .role(Role.valueOf(role))
          .build()
      );

      return jwtDto.getAccessToken();
    } else { // 토큰의 유효 시간이 지났을 경우
      throw new GeneralException(ErrorStatus.TOKEN_EXPIRED, "만료된 토큰입니다.");
    }
  }
}
