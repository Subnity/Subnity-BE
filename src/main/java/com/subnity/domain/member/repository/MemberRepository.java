package com.subnity.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.member.QMember.member;

/**
 * MemberRepository : QueryDSL를 사용한 회원 Repository
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {
  private final JPAQueryFactory queryFactory;

  /**
   * 회원 정보 조회 메서드
   * @param memberId : 회원 ID
   * @return : 회원 정보 조회 응답 객체 반환
   */
  public GetMemberResponse findById(String memberId) {
    return queryFactory.select(
      Projections.fields(
        GetMemberResponse.class,
        member.memberId,
        member.name,
        member.profileUrl,
        member.mail,
        member.isNotification,
        member.role
      )
    )
    .from(member)
    .where(member.memberId.eq(memberId))
    .fetchOne();
  }

  /**
   * 회원 정보 수정 메서드
   * @param request : 회원 정보 수정 요청 객체
   */
  @Transactional
  public void update(UpdateMemberRequest request, String memberId) {
    queryFactory.update(member)
      .set(member.name, request.name())
      .set(member.profileUrl, request.profileUrl())
      .set(member.isNotification, request.isNotification())
      .where(member.memberId.eq(memberId))
      .execute();
  }

  /**
   * 스케줄러 ID 수정
   * @param schedulerId : 스케줄러 ID
   * @param memberId : 회원 ID
   */
  @Transactional
  public void updateSchedulerId(String schedulerId, String memberId) {
    queryFactory.update(member)
      .set(member.schedulerId, schedulerId)
      .where(member.memberId.eq(memberId))
      .execute();
  }
}
