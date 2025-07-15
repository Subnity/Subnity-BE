package com.subnity.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.member.controller.request.UpdateMemberRequest;
import com.subnity.domain.member.controller.response.GetMemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
  private final JPAQueryFactory queryFactory;

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

  @Transactional
  public void update(UpdateMemberRequest request) {
    queryFactory.update(member)
      .set(member.name, request.name())
      .set(member.profileUrl, request.profileUrl())
      .set(member.isNotification, request.isNotification())
      .execute();
  }
}
