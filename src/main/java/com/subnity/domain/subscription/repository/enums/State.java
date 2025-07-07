package com.subnity.domain.subscription.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {

  ACTIVITY("활성화"),
  EXPIRE_SOON("곧 만료"),
  TERMINATION("해지");

  private final String value;
}
