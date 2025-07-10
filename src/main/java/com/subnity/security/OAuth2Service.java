package com.subnity.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {

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
