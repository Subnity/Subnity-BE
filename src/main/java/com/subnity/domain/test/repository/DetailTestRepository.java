package com.subnity.domain.test.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.subnity.domain.test.QTestEntity.testEntity;

@Repository
@RequiredArgsConstructor
public class DetailTestRepository {
  private final JPAQueryFactory queryFactory;

  public TestEntity getTestEntity(Long id) {
    return queryFactory
      .selectFrom(testEntity)
      .where(testEntity.id.eq(id))
      .fetchOne();
  }
}
