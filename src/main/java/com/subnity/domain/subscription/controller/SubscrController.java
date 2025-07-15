package com.subnity.domain.subscription.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.service.SubscrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subscr")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "구독 관련 API")
public class SubscrController {
  private final SubscrService subscrService;

  @PostMapping(value = "/create")
  @Operation(summary = "구독 생성", description = "구독 생성 엔드포인트")
  public ApiResponse<Void> createSubscription(@RequestBody @Valid CreateSubscrRequest request) {
    subscrService.createSubscription(request);
    return ApiResponse.onSuccess();
  }
}
