package com.subnity.domain.member;

import com.subnity.common.config.jpa.BooleanToYNConverter;
import com.subnity.common.config.jpa.CryptoConverter;
import com.subnity.common.domain.BaseTimeEntity;
import com.subnity.domain.member.enums.Role;
import com.subnity.domain.monthly_report.MonthlyReport;
import com.subnity.domain.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {

  @Id
  @Column(name = "member_id")
  private String memberId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "profile_url", nullable = false)
  private String profileUrl;

  @Column(name = "mail", nullable = false)
  @Convert(converter = CryptoConverter.class)
  private String mail;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  @Column(name = "is_notification", nullable = false, columnDefinition = "char(1) default 'N'")
  @Convert(converter = BooleanToYNConverter.class)
  private Boolean isNotification;

  @Column(name = "mail_token")
  private String mailToken;

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  @Column(name = "subscription_id")
  private List<Subscription> subscriptionList = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  @Column(name = "monthly_report_id")
  private List<MonthlyReport> monthlyReportList = new ArrayList<>();
}
