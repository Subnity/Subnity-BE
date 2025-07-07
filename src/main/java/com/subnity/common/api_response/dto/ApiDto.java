package com.subnity.common.api_response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ApiDto {
  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
  private final Boolean isSuccess;
}
