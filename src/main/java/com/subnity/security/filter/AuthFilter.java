package com.subnity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subnity.common.api_response.ApiResponse;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.security.utils.JwtUtils;
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

@Slf4j
public class AuthFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.split("Bearer ")[1];

      if (JwtUtils.getValidateToken(token)) {
        setSecurityContextAuth(token);
      } else {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseObject = objectMapper.writeValueAsString(
          ApiResponse.onFailure(
            ErrorStatus.TOKEN_EXPIRED.getCode(),
            "만료된 토큰입니다.",
            null
          )
        );

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(responseObject);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void setSecurityContextAuth(String token) {
    String memberId = JwtUtils.getMemberId(token);
    String role = JwtUtils.getRole(token);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
      memberId, null, List.of(new SimpleGrantedAuthority(role))
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
