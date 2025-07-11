package com.subnity.security.handler;

import com.subnity.domain.member.Member;
import com.subnity.domain.member.enums.Role;
import com.subnity.domain.member.repository.MemberRepository;
import com.subnity.security.GoogleUser;
import jakarta.servlet.ServletException;
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
  ) throws IOException, ServletException {
    GoogleUser user = (GoogleUser) authentication.getPrincipal();
//    Member findMember = memberRepository.findById(user.getId()).orElse(null);
//
//    if (findMember != null) {
//      memberRepository.save(
//        findMember.toBuilder()
//          .mailToken(user.getAccessToken())
//          .build()
//      );
//    } else {
//      memberRepository.save(
//        Member.builder()
//          .memberId(user.getId())
//          .nickName(user.getName())
//          .role(Role.valueOf(user.getAuthorities().iterator().next().getAuthority()))
//          .profileUrl("")
//          .isNotification(true)
//          .mail(user.getEmail())
//          .mailToken(user.getAccessToken())
//          .build()
//      );
//    }

    Cookie accessToken = new Cookie("AT", user.getAccessToken());
    accessToken.setHttpOnly(true);
    accessToken.setSecure(true);
    accessToken.setPath("/");
    accessToken.setMaxAge(3600);

    response.addCookie(accessToken);
    response.sendRedirect(siteUrl);
    log.info("구글 로그인 성공!");
  }
}
