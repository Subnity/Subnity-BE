package com.subnity.security.utils;

import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private static Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new GeneralException(ErrorStatus.TOKEN_EXPIRED, "만료된 토큰입니다.");
    }

    return authentication;
  }

  public static String getAuthMemberId() {
    Authentication authentication = getAuthentication();
    return authentication.getName();
  }
}
