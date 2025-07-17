package com.subnity.domain.member.utils;

import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.repository.JpaMemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtils {
  private final JpaMemberRepository nonJpaMemberRepository;
  private static JpaMemberRepository jpaMemberRepository;

  @PostConstruct
  public void init() {
    jpaMemberRepository = this.nonJpaMemberRepository;
  }

  /*
   * 구조 개선 필요
   */
  public static Member getMember(String memberId) {
    return jpaMemberRepository.findById(memberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "회원을 찾을 수 없습니다."));
  }
}
