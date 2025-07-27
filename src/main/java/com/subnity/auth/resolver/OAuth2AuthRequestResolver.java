package com.subnity.auth.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2AuthRequestResolver : OAuth2 Url를 커스텀할 수 있는 Resolver (Google Refresh Token 발급을 위함)
 */
public class OAuth2AuthRequestResolver implements OAuth2AuthorizationRequestResolver {
  private final OAuth2AuthorizationRequestResolver defaultResolver;

  public OAuth2AuthRequestResolver(ClientRegistrationRepository repo, String authorizationRequestBaseUri) {
    this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri);
  }

  @Override
  public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
    OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
    return customizeAuthorizationRequest(req);
  }

  @Override
  public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
    OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
    return customizeAuthorizationRequest(req);
  }

  private OAuth2AuthorizationRequest customizeAuthorizationRequest(OAuth2AuthorizationRequest req) {
    if (req == null) return null;

    Map<String, Object> extraParams = new HashMap<>(req.getAdditionalParameters());
    extraParams.put("access_type", "offline");
    extraParams.put("prompt", "consent");

    return OAuth2AuthorizationRequest.from(req)
      .additionalParameters(extraParams)
      .build();
  }
}
