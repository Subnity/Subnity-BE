package com.subnity.domain.member.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import com.subnity.domain.member.repository.JpaMemberRepository;
import com.subnity.domain.member.repository.MemberRepository;
import com.subnity.domain.member.utils.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * MemberService : 회원 관련 Service
 */
@Service
@RequiredArgsConstructor
public class MemberService {
  private final JpaMemberRepository jpaMemberRepository;
  private final MemberRepository memberRepository;

  /**
   * 회원 정보 조회 메서드
   * @return : 회원 정보 객체 반환
   */
  public GetMemberResponse getMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    return GetMemberResponse.builder()
      .memberId(memberId)
      .role(member.getRole())
      .name(member.getName())
      .mail(member.getMail())
      .isNotification(member.getIsNotification())
      .profileUrl(member.getProfileUrl())
      .build();
  }

  /**
   * 회원 정보 수정 메서드
   * @param request : 회원 수정 요청 객체
   */
  public void updateMember(UpdateMemberRequest request) {
    String memberId = SecurityUtils.getAuthMemberId();
    memberRepository.update(request, memberId);
  }

  /**
   * 회원 정보(데이터) 영구 삭제 메서드
   */
  public void deleteMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    jpaMemberRepository.deleteById(memberId);
  }
}
