package com.subnity.domain.member.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import com.subnity.domain.member.repository.GetMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final GetMemberRepository repository;

  public GetMemberResponse getMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    return repository.findById(memberId);
  }
}
