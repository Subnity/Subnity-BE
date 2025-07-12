package com.subnity.security.dto;

import com.subnity.domain.member.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtClaimsDto {
  private String memberId;
  private Role role;
}
