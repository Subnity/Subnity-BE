package com.subnity.domain.payment_history.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

  SUCCESS("성공"),
  FAILURE("실패");

  private final String value;
}
