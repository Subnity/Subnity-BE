package com.subnity.domain.payment_history;

import com.subnity.common.domain.BaseTimeEntity;
import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.domain.member.Member;
import com.subnity.domain.payment_history.controller.response.GetPaymentHistoryResponse;
import com.subnity.domain.payment_history.enums.PaymentStatus;
import com.subnity.domain.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
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
  private long cost;

  @Column(name = "payment_date", nullable = false)
  private LocalDate paymentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "subscription_category", nullable = false)
  private SubscrCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id", nullable = false)
  private Subscription subscription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;


  public static GetPaymentHistoryResponse from(PaymentHistory paymentHistory) {
    DecimalFormat formatter = new DecimalFormat("#,###");

    return GetPaymentHistoryResponse.builder()
      .paymentHistoryId(paymentHistory.getPaymentHistoryId())
      .subscrId(paymentHistory.subscription.getSubscriptionId())
      .paymentStatus(paymentHistory.getPaymentStatus())
      .paymentDate(paymentHistory.paymentDate)
      .cost(formatter.format(paymentHistory.getCost()))
      .build();
  }
}
