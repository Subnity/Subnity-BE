package com.subnity.domain.member.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.member.controller.response.DetailMemberResponse;
import com.subnity.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {
  private final MemberService memberService;

  @GetMapping(value = "")
  @Operation(summary = "회원 조회", description = "인증된 회원 조회 엔드포인트")
  public ApiResponse<DetailMemberResponse> getMember() {
    return ApiResponse.onSuccess(memberService.getMember());
  }
}
