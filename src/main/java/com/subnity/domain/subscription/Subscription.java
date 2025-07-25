package com.subnity.domain.subscription;

import com.subnity.common.config.jpa.BooleanToYNConverter;
import com.subnity.common.domain.BaseTimeEntity;
import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.payment_history.PaymentHistory;
import com.subnity.domain.subscription.enums.PaymentCycle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "subscription")
public class Subscription extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subscription_id", nullable = false)
  private Long subscriptionId;

  @Column(name = "platform_name", nullable = false)
  private String platformName;

  @Column(name = "description")
  private String description;

  @Column(name = "cost", nullable = false)
  private long cost;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_cycle", nullable = false)
  private PaymentCycle paymentCycle;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private SubscrStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private SubscrCategory category;

  @Column(name = "is_notification", nullable = false, columnDefinition = "char(1) default 'N'")
  @Convert(converter = BooleanToYNConverter.class)
  private Boolean isNotification;

  @Column(name = "cancelled_at")
  private LocalDate cancelledAt;

  @Column(name = "last_payment_date", nullable = false)
  private LocalDate lastPaymentDate;

  @Column(name = "next_payment_date")
  private LocalDate nextPaymentDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder.Default
  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
  private List<PaymentHistory> paymentHistoryList = new ArrayList<>();
}
