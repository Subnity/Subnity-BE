package com.subnity.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Value("${server.site_url}")
  private String siteUrl;

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

    log.error("구글 로그인 실패", exception);
  }
}
