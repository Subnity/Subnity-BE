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
public class CategoryExpense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_expense_id")
  private Long categoryExpenseId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "monthly_report_id")
  private MonthlyReport monthlyReport;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private SubscrCategory category;

  @Column(name = "total_expense", nullable = false)
  private String totalExpense;

  @Column(name = "ratio", nullable = false)
  private String ratio;
}
