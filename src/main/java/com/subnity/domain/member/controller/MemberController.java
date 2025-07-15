package com.subnity.domain.member.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import com.subnity.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {
  private final MemberService memberService;

  @GetMapping(value = "")
  @Operation(summary = "회원 정보 조회", description = "회원 조회 엔드포인트")
  public ApiResponse<GetMemberResponse> getMember() {
    return ApiResponse.onSuccess(memberService.getMember());
  }

  @PatchMapping(value = "/update")
  @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 엔드포인트")
  public ApiResponse<GetMemberResponse> updateMember(@RequestBody @Valid UpdateMemberRequest request) {
    memberService.updateMember(request);
    return ApiResponse.onSuccess();
  }
}
