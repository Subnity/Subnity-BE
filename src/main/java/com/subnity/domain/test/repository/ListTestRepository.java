package com.subnity.domain.test.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subnity.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.subnity.domain.test.QTestEntity.testEntity;

@Repository
@RequiredArgsConstructor
public class ListTestRepository {
  private final JPAQueryFactory queryFactory;

  public List<TestEntity> getTestEntityList() {
    return queryFactory
      .selectFrom(testEntity)
      .fetch();
  }
}
