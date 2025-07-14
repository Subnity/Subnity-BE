package com.subnity.auth.handler;

import com.subnity.domain.member.Member;
import com.subnity.domain.member.enums.Role;
import com.subnity.domain.member.repository.MemberRepository;
import com.subnity.auth.GoogleUser;
import com.subnity.auth.dto.JwtBuilder;
import com.subnity.auth.dto.JwtClaimsDto;
import com.subnity.auth.utils.JwtUtils;
import com.subnity.auth.utils.RedisUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * AuthSuccessHandler : OAuth2 인증에 성공할 경우 호출될 핸들러
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final MemberRepository memberRepository;

  @Value("${server.site_url}")
  private String siteUrl;

  /**
   * OAuth2 인증에 성공할 경우 실행될 메서드
   * @param request : HttpServletRequest 객체
   * @param response : HttpServletResponse 객체
   * @param authentication 인증에 성공한 회원 정보
   * @throws IOException : 예외
   */
  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws IOException {
    GoogleUser user = (GoogleUser) authentication.getPrincipal();
    Role role = Role.valueOf(user.getAuthorities().iterator().next().getAuthority());
    Member findMember = memberRepository.findById(user.getId()).orElse(null);

    // 이미 로그인한 회원이 존재하는지 여부
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
          .name(user.getName())
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
      .role(role)
      .build();

    JwtBuilder jwtDto = JwtUtils.createToken(jwtClaimsDto);

    // Redis에 Refresh Token 저장
    RedisUtils.save(jwtDto.getRefreshToken());

    Cookie refreshTokenCookie = new Cookie("RT", jwtDto.getRefreshToken());
    refreshTokenCookie.setMaxAge(605000);
    refreshTokenCookie.setPath("/");

    response.addCookie(refreshTokenCookie);
    response.setContentType("text/html;charset=UTF-8");

    // React 사용할 시 코드 수정 필요
    response.getWriter().write(
      String.format(
        """
          <script>
            window.opener.postMessage({ accessToken: '%s' }, '%s');
            window.close();
          </script>
        """,
        jwtDto.getAccessToken(), siteUrl
      )
    );

    log.info("구글 로그인 성공");
  }
}
