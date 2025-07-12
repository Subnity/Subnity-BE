package com.subnity.security.handler;

import com.subnity.domain.member.Member;
import com.subnity.domain.member.enums.Role;
import com.subnity.domain.member.repository.MemberRepository;
import com.subnity.security.GoogleUser;
import com.subnity.security.dto.JwtClaimsDto;
import com.subnity.security.utils.JwtUtils;
import com.subnity.security.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final MemberRepository memberRepository;

  @Value("${server.site_url}")
  private String siteUrl;

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws IOException {
    GoogleUser user = (GoogleUser) authentication.getPrincipal();
    Role role = Role.valueOf(user.getAuthorities().iterator().next().getAuthority());
    Member findMember = memberRepository.findById(user.getId()).orElse(null);

    if (findMember != null) {
      memberRepository.save(
        findMember.toBuilder()
          .mailToken(user.getAccessToken())
          .build()
      );
    } else {
      memberRepository.save(
        Member.builder()
          .memberId(user.getId())
          .nickName(user.getName())
          .role(role)
          .profileUrl("") // 추후에 추가 예정
          .isNotification(true)
          .mail(user.getEmail())
          .mailToken(user.getAccessToken())
          .build()
      );
    }

    JwtClaimsDto jwtClaimsDto = JwtClaimsDto.builder()
      .memberId(user.getId())
      .memberName(user.getName())
      .role(role)
      .build();

    String accessToken = JwtUtils.createAccessToken(jwtClaimsDto);
    String refreshToken = JwtUtils.createRefreshToken(jwtClaimsDto);

    // Redis에 Refresh Token 저장
    RedisUtils.save(refreshToken);

    response.setContentType("text/html;charset=UTF-8");
    response.getWriter().write(
      String.format(
        """
          <script>
            window.opener.postMessage({ accessToken: '%s' }, '%s');
            window.close();
          </script>
        """,
        accessToken, siteUrl
      )
    );

    log.info("Google Login Success!!");
  }
}
