package com.subnity.domain.member.controller.response;

import com.subnity.domain.member.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 정보 조회 응답 객체")
public class DetailMemberResponse {

  @Schema(description = "회원 ID")
  private String memberId;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "프로필 사진 URL")
  private String profileUrl;

  @Schema(description = "메일 주소")
  private String mail;

  @Schema(description = "권한")
  private Role role;

  @Schema(description = "알림 여부")
  private Boolean isNotification;
}
