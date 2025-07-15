package com.subnity.domain.member.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import com.subnity.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository repository;

  public GetMemberResponse getMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    return repository.findById(memberId);
  }

  public void updateMember(UpdateMemberRequest request) {
    repository.update(request);
  }
}
