package com.subnity.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.member.controller.response.DetailMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class DetailMemberRepository {
  private final JPAQueryFactory queryFactory;

  public DetailMemberResponse findById(String memberId) {
    return queryFactory.select(
      Projections.fields(
        DetailMemberResponse.class,
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
}
