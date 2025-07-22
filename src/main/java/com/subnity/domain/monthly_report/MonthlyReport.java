package com.subnity.domain.monthly_report;

import com.subnity.domain.member.Member;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "monthly_report")
public class MonthlyReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id", nullable = false)
  private Long reportId;

  @Column(name = "year", nullable = false)
  private int year;

  @Column(name = "month", nullable = false)
  private int month;

  @Column(name = "total_payment")
  private String totalPayment;

  @Column(name = "active_subscription_count")
  private int activeSubscrCount;

  @Column(name = "before_contrast")
  private String beforeContrast;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder.Default
  @OneToMany(mappedBy = "monthlyReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<CategoryCost> categoryExpenseList = new ArrayList<>();
}
