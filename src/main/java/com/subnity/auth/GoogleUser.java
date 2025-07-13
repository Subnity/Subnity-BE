package com.subnity.auth;

import com.subnity.domain.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * GoogleUser : Google OAuth2 인증된 사용자 계정 정보 객체
 */
@Builder
@Getter
@AllArgsConstructor
public class GoogleUser implements OAuth2User {

  private final Map<String, Object> attributes;
  private final String registrationId;
  private final String accessToken;

  /**
   * 사용자 계정 정보 Json
   * @return : 사용자 계정 정보 Json 반환
   */
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  /**
   * 권한 부여 메서드
   * @return : 권한 목록 반환
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(Role.USER.name()));
  }

  /**
   * 계정 이름 얻는 메서드
   * @return : 사용자 계정 이름 반환
   */
  @Override
  public String getName() {
    return attributes.get("name").toString();
  }

  /**
   * google + 등록 ID로 회원 ID를 만드는 메서드
   * @return : 회원 ID 반환
   */
  public String getId() {
    return registrationId + "-" + attributes.get("sub").toString();
  }

  /**
   * 계정 고유 ID 얻는 메서드
   * @return : 사용자 계정 고유 ID 반환
   */
  public String getProviderId() {
    return attributes.get("sub").toString();
  }

  /**
   * 계정 이메일 얻는 메서드
   * @return : 사용자 계정 이메일 반환
   */
  public String getEmail() {
    return attributes.get("email").toString();
  }
}
