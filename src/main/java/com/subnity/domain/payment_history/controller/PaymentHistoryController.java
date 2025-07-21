package com.subnity.domain.payment_history.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.GetPaymentHistoryResponse;
import com.subnity.domain.payment_history.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PaymentHistoryController : 결제 히스토리 Controller
 */
@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
@Tag(name = "Payment History", description = "결제 히스토리 관련 API")
public class PaymentHistoryController {
  private final PaymentHistoryService paymentHistoryService;

  /**
   * 결제 히스토리 등록 엔드포인트
   * @param request : 결제 히스토리 생성 요청 객체
   * @return : 공통 응답 객체 반환
   */
  @PostMapping(value = "/create")
  @Operation(summary = "결제 히스토리 등록", description = "결제 히스토리 등록 엔드포인트")
  public ApiResponse<Void> createPaymentHistory(@RequestBody @Valid CreatePaymentHistoryRequest request) {
    paymentHistoryService.createPaymentHistory(request);
    return ApiResponse.onSuccess();
  }

  /**
   * 특정 결제 히스토리 조회 엔드포인트
   * @param paymentHistoryId : 결제 히스토리 ID
   * @return : 결제 히스토리 조회 응답 객체 반환
   */
  @GetMapping(value = "/{id}")
  @Operation(summary = "특정 결제 히스토리 조회", description = "특정 결제 히스토리 조회 엔드포인트")
  public ApiResponse<GetPaymentHistoryResponse> getPaymentHistory(@PathVariable("id") String paymentHistoryId) {
    return ApiResponse.onSuccess(paymentHistoryService.getPaymentHistory(paymentHistoryId));
  }

  /**
   * 회원 결제 히스토리 목록 조회 엔드포인트
   * @return : 결제 히스토리 목록 조회 응답 객체 반환
   */
  @GetMapping(value = "/list")
  @Operation(summary = "결제 히스토리 목록 조회", description = "결제 히스토리 목록 조회 엔드포인트")
  public ApiResponse<List<GetPaymentHistoryResponse>> getPaymentHistoryList() {
    return ApiResponse.onSuccess(paymentHistoryService.getPaymentHistoryList());
  }

  /**
   * 특정 구독 결제 히스토리 목록 조회 엔드포인트
   * @param subscrId : 구독 ID
   * @return : 결제 히스토리 목록 조회 응답 객체 반환
   */
  @GetMapping(value = "/list/{subscrId}")
  @Operation(summary = "특정 구독 결제 히스토리 목록 조회", description = "특정 구독 결제 히스토리 목록 조회 엔드포인트")
  public ApiResponse<List<GetPaymentHistoryResponse>> getPaymentHistoryListBySubscrId(@PathVariable("subscrId") String subscrId) {
    return ApiResponse.onSuccess(paymentHistoryService.getPaymentHistoryListBySubscrId(subscrId));
  }

  /**
   * 특정 결제 히스토리 삭제 엔드포인트
   * @param paymentHistoryId : 결제 히스토리 ID
   * @return : 공통 응답 객체 반환
   */
  @DeleteMapping(value = "/delete/{id}")
  @Operation(summary = "특정 결제 히스토리 삭제", description = "특정 결제 히스토리 삭제 엔드포인트")
  public ApiResponse<Void> deletePaymentHistory(@PathVariable("id") String paymentHistoryId) {
    paymentHistoryService.deletePaymentHistory(paymentHistoryId);
    return ApiResponse.onSuccess();
  }
}
