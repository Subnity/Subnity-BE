package com.subnity.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * AuthFailureHandler : OAuth2 인증에 실패할 경우 호출될 핸들러
 */
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Value("${server.site_url}")
  private String siteUrl;

  /**
   * OAuth2 인증에 실패할 경우 실행될 메서드
   * @param request : HttpServletRequest 객체
   * @param response : HttpServletResponse 객체
   * @param exception : 인증 예외
   * @throws IOException : 예외
   */
  @Override
  public void onAuthenticationFailure(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception
  ) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    response.getWriter().write(
      String.format(
        """
          <script>
            window.opener.postMessage('%s');
            window.close();
          </script>
        """,
        siteUrl
      )
    );

    log.error("Google login failed", exception);
  }
}
