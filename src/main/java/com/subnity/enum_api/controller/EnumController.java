package com.subnity.enum_api.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.enum_api.controller.response.GetEnumResponse;
import com.subnity.enum_api.service.EnumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/enum")
@RequiredArgsConstructor
@Tag(name = "Enum", description = "Enum 관련 API")
public class EnumController {
  private final EnumService enumService;

  @GetMapping(value = "/subscription-category")
  @Operation(summary = "구독 카테고리", description = "구독 카테고리 Enum 목록 조회")
  public ApiResponse<GetEnumResponse> getSubscrCategoryEnumList() {
    return ApiResponse.onSuccess(enumService.getSubscrCategoryEnumList());
  }

  @GetMapping(value = "/subscription-status")
  @Operation(summary = "구독 상태", description = "구독 상태 Enum 목록 조회")
  public ApiResponse<GetEnumResponse> getSubscrStatusEnumList(SecurityContext securityContext) {
    System.out.println(securityContext.getAuthentication().getName());
    return ApiResponse.onSuccess(enumService.getSubscrStatusEnumList());
  }

  @GetMapping(value = "/payment-cycle")
  @Operation(summary = "결제 주기", description = "결제 주기 Enum 목록 조회")
  public ApiResponse<GetEnumResponse> getPaymentCycleEnumList() {
    return ApiResponse.onSuccess(enumService.getPaymentCycleEnumList());
  }
}
