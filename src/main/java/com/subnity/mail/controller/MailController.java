package com.subnity.mail.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.mail.service.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Tag(name = "Mail", description = "메일 관련 API")
public class MailController {
  private final MailService mailService;

  @GetMapping("/test")
  public ApiResponse<Void> test() {
    mailService.mailSearchByKeyword("가비아");
    return ApiResponse.onSuccess();
  }
}
