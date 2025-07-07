package com.subnity.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {

  CREDIT("신용카드"),
  CHECK("체크카드"),
  SIMPLE_PAYMENT("간편결제"),
  BANKBOOK_DEPOSIT("무통장 입금"),
  SMALL_PAYMENT("소액 결제"),
  ETC("기타");

  private final String value;
}
