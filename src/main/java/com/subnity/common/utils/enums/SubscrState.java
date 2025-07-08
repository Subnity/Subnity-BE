package com.subnity.common.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubscrState {

  ACTIVITY("활성화"),
  EXPIRE_SOON("곧 만료"),
  CANCEL("해지");

  private final String value;
}
