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

/**
 * MemberController : 회원 관련 Controller
 */
@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {
  private final MemberService memberService;

  /**
   * 회원 정보 조회 엔드포인트
   * @return : 회원 정보 조회 응답 객체 반환
   */
  @GetMapping(value = "")
  @Operation(summary = "회원 정보 조회", description = "회원 조회 엔드포인트")
  public ApiResponse<GetMemberResponse> getMember() {
    return ApiResponse.onSuccess(memberService.getMember());
  }

  /**
   * 회원 정보 부분 수정 엔드포인트
   * @param request : 회원 수정 요청 객체
   * @return : 응답 성공 객체 반환
   */
  @PatchMapping(value = "/update")
  @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 엔드포인트")
  public ApiResponse<GetMemberResponse> updateMember(@RequestBody @Valid UpdateMemberRequest request) {
    memberService.updateMember(request);
    return ApiResponse.onSuccess();
  }

  /**
   * 회원 정보(데이터) 영구 삭제 엔드포인트
   * @return : 응답 성공 객체 반환
   */
  @DeleteMapping(value = "/delete")
  @Operation(summary = "회원 정보 영구 삭제", description = "회원 정보(데이터) 영구 삭제하는 엔드포인트")
  public ApiResponse<Void> deleteMember() {
    memberService.deleteMember();
    return ApiResponse.onSuccess();
  }
}
