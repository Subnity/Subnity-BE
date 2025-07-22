package com.subnity.domain.monthly_report;

import com.subnity.common.utils.enums.SubscrCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "category_expense")
public class CategoryCost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_cost_id")
  private Long categoryExpenseId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "monthly_report_id")
  private MonthlyReport monthlyReport;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private SubscrCategory category;

  @Column(name = "total_cost", nullable = false)
  private String totalCost;

  @Column(name = "ratio", nullable = false)
  private int ratio;
}
