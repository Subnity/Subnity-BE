package com.subnity.security.service;

import com.subnity.domain.member.enums.Role;
import com.subnity.security.dto.JwtBuilder;
import com.subnity.security.dto.JwtClaimsDto;
import com.subnity.security.utils.JwtUtils;
import com.subnity.security.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  public String recreateAccessToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.split("Bearer ")[1];
      String refreshToken = RedisUtils.get(token);

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
      }
    }

    return null;
  }
}
