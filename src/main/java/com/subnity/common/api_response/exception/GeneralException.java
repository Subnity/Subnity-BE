package com.subnity.common.api_response.exception;

import com.subnity.common.api_response.dto.ApiDto;
import com.subnity.common.api_response.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
  private final BaseErrorCode code;

  public GeneralException(ErrorStatus errorStatus, String message) {
    super(errorStatus.getMessage(message));
    this.code = errorStatus;
  }

  public ApiDto getErrorReasonHttpStatus() {
    return this.code.getResponseHttpStatus();
  }
}
