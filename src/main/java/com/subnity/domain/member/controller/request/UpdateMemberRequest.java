package com.subnity.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 정보 수정 요청 객체")
public record UpdateMemberRequest(

  @Schema(description = "이름")
  String name,

  @Schema(description = "프로필 사진 URL")
  String profileUrl,

  @Schema(description = "알림 여부")
  Boolean isNotification
) { }
