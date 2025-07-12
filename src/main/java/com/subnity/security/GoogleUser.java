package com.subnity.security;

import com.subnity.domain.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Builder
@Getter
@AllArgsConstructor
public class GoogleUser implements OAuth2User {

  private final Map<String, Object> attributes;
  private final String registrationId;
  private final String accessToken;

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(Role.USER.name()));
  }

  @Override
  public String getName() {
    return attributes.get("name").toString();
  }

  public String getId() {
    return registrationId + "-" + attributes.get("sub").toString();
  }

  public String getProviderId() {
    return attributes.get("sub").toString();
  }

  public String getEmail() {
    return attributes.get("email").toString();
  }
}
