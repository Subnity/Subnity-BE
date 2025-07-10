package com.subnity.security.dto;

import com.subnity.domain.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserDto {
  private String id;
  private String name;
  private String email;
  private Role role;
}
