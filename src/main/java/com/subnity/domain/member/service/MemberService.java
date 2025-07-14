package com.subnity.domain.member.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.controller.response.DetailMemberResponse;
import com.subnity.domain.member.repository.DetailMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final DetailMemberRepository repository;

  public DetailMemberResponse getMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    return repository.findById(memberId);
  }
}
