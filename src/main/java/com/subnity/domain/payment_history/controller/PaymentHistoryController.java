package com.subnity.domain.payment_history.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
@Tag(name = "Payment History", description = "결제 히스토리 관련 API")
public class PaymentHistoryController {
  private final PaymentHistoryService paymentHistoryService;

  @PostMapping(value = "/create")
  @Operation(summary = "결제 히스토리 등록", description = "결제 히스토리 등록 엔드포인트")
  public ApiResponse<Void> createPaymentHistory(@RequestBody @Valid CreatePaymentHistoryRequest request) {
    paymentHistoryService.createPaymentHistory(request);
    return ApiResponse.onSuccess();
  }
}
