package com.subnity.domain.member.utils;

import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.repository.JpaMemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * MemberUtils : 회원 관련 유틸 클래스
 */
@Component
@RequiredArgsConstructor
public class MemberUtils {
  private final JpaMemberRepository nonJpaMemberRepository;
  private static JpaMemberRepository jpaMemberRepository;

  @PostConstruct
  public void init() {
    jpaMemberRepository = this.nonJpaMemberRepository;
  }

  /**
   * 인증된 회원 검색 메서드
   * @param memberId : 회원 ID
   * @return : 회원 반환
   */
  public static Member getMember(String memberId) {
    return jpaMemberRepository.findById(memberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "회원을 찾을 수 없습니다."));
  }
}
