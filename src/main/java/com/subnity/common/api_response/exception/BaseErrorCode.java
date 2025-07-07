package com.subnity.common.api_response.exception;

import com.subnity.common.api_response.dto.ApiDto;

public interface BaseErrorCode {
  ApiDto getResponseHttpStatus();
}
