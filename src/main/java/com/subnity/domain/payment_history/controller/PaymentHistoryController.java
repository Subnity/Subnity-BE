package com.subnity.domain.payment_history.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.DetailPaymentHistoryResponse;
import com.subnity.domain.payment_history.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping(value = "/{id}")
  @Operation(summary = "특정 결제 히스토리 조회", description = "특정 결제 히스토리 조회 엔드포인트")
  public ApiResponse<DetailPaymentHistoryResponse> getPaymentHistory(@PathVariable("id") String paymentHistoryId) {
    return ApiResponse.onSuccess(paymentHistoryService.getPaymentHistory(paymentHistoryId));
  }

  @GetMapping(value = "/list")
  @Operation(summary = "결제 히스토리 목록 조회", description = "결제 히스토리 목록 조회 엔드포인트")
  public ApiResponse<List<DetailPaymentHistoryResponse>> getPaymentHistoryList() {
    return ApiResponse.onSuccess(paymentHistoryService.getPaymentHistoryList());
  }
}
