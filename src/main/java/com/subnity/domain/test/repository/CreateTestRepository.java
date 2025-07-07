package com.subnity.domain.test.repository;

import com.subnity.domain.test.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateTestRepository extends JpaRepository<TestEntity, Long> {
}
