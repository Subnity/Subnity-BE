package com.subnity.domain.monthly_report.repository;

import com.subnity.domain.monthly_report.CategoryCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryExpenseRepository extends JpaRepository<CategoryCost, Long> {
}
