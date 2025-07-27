package com.subnity.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subnity.common.api_response.ApiResponse;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.auth.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * AuthFilter : JWT 인증 필터
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

  private final List<String> EXCLUDE_URLS = List.of(
    "/enum"
  );

  /**
   * JWT 인증을 위한 필터 메서드
   * @param request : HttpServletRequest 객체
   * @param response : HttpServletResponse 객체
   * @param filterChain : FilterChain 객체
   * @throws ServletException : 예외
   * @throws IOException : 예외
   */
  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    for (String uri : EXCLUDE_URLS) {
      if (request.getRequestURI().contains(uri)) { // 해당 자원이 EXCLUDE_URLS에 있는 자원이면 필터 건너뜀
        filterChain.doFilter(request, response);
        return;
      }
    }

    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.split("Bearer ")[1];

      if (JwtUtils.getValidateToken(token)) {
        setSecurityContextAuth(token);
      } else { // Access Token의 유효 시간이 만료됐을 경우
        ObjectMapper objectMapper = new ObjectMapper();
        String responseObject = objectMapper.writeValueAsString(
          ApiResponse.onFailure(ErrorStatus.TOKEN_EXPIRED.getCode(), "만료된 토큰입니다.", null)
        );

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseObject);
        return;
      }
    } else {
      // Access Token 재발급을 위한 로직
      if(request.getRequestURI().equals("/auth/refresh")) {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseObject = objectMapper.writeValueAsString(
          ApiResponse.onFailure(ErrorStatus.UNAUTHORIZED.getCode(), "접근할 수 있는 권한이 없습니다.", null)
        );

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseObject);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * 회원 인증 정보를 SecurityContextHolder에 저장하기 위한 메서드
   * @param token : Access Token
   */
  private void setSecurityContextAuth(String token) {
    String memberId = JwtUtils.getMemberId(token);
    String role = JwtUtils.getRole(token);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
      memberId, null, List.of(new SimpleGrantedAuthority(role))
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
