package com.subnity.domain.enum_api.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.enum_api.controller.response.ListEnumResponse;
import com.subnity.domain.enum_api.service.EnumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EnumController : Enum 관련 Controller
 */
@RestController
@RequestMapping(value = "/enum")
@RequiredArgsConstructor
@Tag(name = "Enum(선택형 필드)", description = "Enum 관련 API")
public class EnumController {
  private final EnumService enumService;

  /**
   * 구독 카테고리 목록 조회
   * @return : 구독 카테고리 Enum 목록 반환
   */
  @GetMapping(value = "/subscription-category")
  @Operation(summary = "구독 카테고리", description = "구독 카테고리 Enum 목록 조회")
  public ApiResponse<ListEnumResponse> getSubscrCategoryEnumList() {
    return ApiResponse.onSuccess(enumService.getSubscrCategoryEnumList());
  }

  /**
   * 구독 상태 목록 조회
   * @return : 구독 상태 Enum 목록 반환
   */
  @GetMapping(value = "/subscription-status")
  @Operation(summary = "구독 상태", description = "구독 상태 Enum 목록 조회")
  public ApiResponse<ListEnumResponse> getSubscrStatusEnumList() {
    return ApiResponse.onSuccess(enumService.getSubscrStatusEnumList());
  }

  /**
   * 결제 주기 목록 조회
   * @return : 결제 주기 Enum 목록 반환
   */
  @GetMapping(value = "/payment-cycle")
  @Operation(summary = "결제 주기", description = "결제 주기 Enum 목록 조회")
  public ApiResponse<ListEnumResponse> getPaymentCycleEnumList() {
    return ApiResponse.onSuccess(enumService.getPaymentCycleEnumList());
  }

  /**
   * 결제 상태 목록 조회
   * @return : 결제 상태 Enum 목록 반환
   */
  @GetMapping(value = "/payment-status")
  @Operation(summary = "결제 상태", description = "결제 상태 Enum 목록 조회")
  public ApiResponse<ListEnumResponse> getPaymentStatusEnumList() {
    return ApiResponse.onSuccess(enumService.getPaymentStatusEnumList());
  }
}
