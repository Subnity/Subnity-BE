package com.subnity.domain.subscription.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentCycle {

  MONTH("월"),
  YEAR("년");

  private final String value;
}
