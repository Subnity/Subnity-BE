package com.subnity.mail.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDto {
  private List<Map<String, Object>> headers;
  private Map<String, Object> body;
}
