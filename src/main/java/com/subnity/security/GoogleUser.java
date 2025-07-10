package com.subnity.security;

import com.subnity.domain.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
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
    Collection<GrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return Role.USER.name();
      }
    });

    return authorityList;
  }

  @Override
  public String getName() {
    return attributes.get("name").toString();
  }

  public String getId() {
    return "google-" + registrationId;
  }

  public String getProviderId() {
    return attributes.get("sub").toString();
  }

  public String getEmail() {
    return attributes.get("email").toString();
  }
}
