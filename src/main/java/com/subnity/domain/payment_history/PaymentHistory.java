package com.subnity.domain.payment_history;

import com.subnity.common.domain.BaseTimeEntity;
import com.subnity.domain.payment_history.enums.PaymentStatus;
import com.subnity.domain.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistory extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_history_id", nullable = false)
  private Long paymentHistoryId;

  @Column(name = "cost", nullable = false)
  private String cost;

  @Column(name = "payment_date", nullable = false)
  private LocalDateTime paymentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id")
  private Subscription subscription;
}
