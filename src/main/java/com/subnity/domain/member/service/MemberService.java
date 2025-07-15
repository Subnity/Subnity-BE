package com.subnity.domain.member.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import com.subnity.domain.member.repository.JpaMemberRepository;
import com.subnity.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final JpaMemberRepository jpaMemberRepository;

  public GetMemberResponse getMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    return memberRepository.findById(memberId);
  }

  public void updateMember(UpdateMemberRequest request) {
    memberRepository.update(request);
  }

  public void deleteMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    jpaMemberRepository.deleteById(memberId);
  }
}
