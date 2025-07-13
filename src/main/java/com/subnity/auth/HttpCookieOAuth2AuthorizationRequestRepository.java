package com.subnity.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.SerializationUtils;
import org.springframework.web.util.WebUtils;

import java.util.Base64;
import java.util.Optional;

public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

  private static final String COOKIE_NAME = "oauth2_auth_request";
  private static final int COOKIE_EXPIRE_SECONDS = 180;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return getCookie(request)
      .map(this::deserialize)
      .orElse(null);
  }

  @Override
  public void saveAuthorizationRequest(
    OAuth2AuthorizationRequest authorizationRequest,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    if (authorizationRequest == null) {
      deleteCookie(response);
      return;
    }

    Cookie cookie = new Cookie(COOKIE_NAME, serialize(authorizationRequest));
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setMaxAge(COOKIE_EXPIRE_SECONDS);
    response.addCookie(cookie);
  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
    return loadAuthorizationRequest(request);
  }

  private Optional<Cookie> getCookie(HttpServletRequest request) {
    return Optional.ofNullable(WebUtils.getCookie(request, COOKIE_NAME));
  }

  private String serialize(OAuth2AuthorizationRequest authRequest) {
    return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(authRequest));
  }

  private OAuth2AuthorizationRequest deserialize(Cookie cookie) {
    return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
      Base64.getUrlDecoder().decode(cookie.getValue())
    );
  }

  private void deleteCookie(HttpServletResponse response) {
    Cookie cookie = new Cookie(COOKIE_NAME, "");
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}

