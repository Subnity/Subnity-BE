package com.subnity.auth.service;

import com.subnity.auth.GoogleUser;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2Service : OAuth2 인증이 마친 후 구글 계정 사용자의 정보를 얻기 위한 서비스 클래스
 */
@Service
public class OAuth2Service extends DefaultOAuth2UserService {

  /**
   * Google 사용자 계정의 정보를 얻는 메서드
   * @param userRequest : OAuth2 인증 정보
   * @return : GoogleUser 객체 반환
   * @throws OAuth2AuthenticationException : OAuth2 인증 예외
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = super.loadUser(userRequest); // 구글 attributes를 얻기 위함
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String accessToken = userRequest.getAccessToken().getTokenValue();

    return GoogleUser.builder()
      .registrationId(registrationId)
      .accessToken(accessToken)
      .attributes(user.getAttributes())
      .build();
  }
}
