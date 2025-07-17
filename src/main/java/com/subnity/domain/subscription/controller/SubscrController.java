package com.subnity.domain.subscription.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.controller.request.UpdateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import com.subnity.domain.subscription.service.SubscrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping(value = "/{id}")
  @Operation(summary = "구독 정보 조회", description = "구독 ID를 통해서 하나의 구독 정보를 가져오는 엔드포인트")
  public ApiResponse<GetSubscrResponse> getSubscr(@PathVariable("id") String subscriptionId) {
    return ApiResponse.onSuccess(subscrService.getSubscr(subscriptionId));
  }

  @GetMapping(value = "/list")
  @Operation(summary = "구독 목록 조회", description = "구독 목록 조회 엔드포인트")
  public ApiResponse<List<GetSubscrResponse>> getSubscrList() {
    return ApiResponse.onSuccess(subscrService.getSubscrList());
  }

  @PatchMapping(value = "/update")
  @Operation(summary = "구독 정보 수정", description = "구독 정보 수정 엔드포인트")
  public ApiResponse<Void> updateSubscription(@RequestBody @Valid UpdateSubscrRequest request) {
    subscrService.updateSubscription(request);
    return ApiResponse.onSuccess();
  }

  @DeleteMapping(value = "/delete/{id}")
  @Operation(summary = "구독 제거", description = "구독 정보를 제거하는 엔드포인트")
  public ApiResponse<Void> deleteSubscription(@PathVariable("id") String subscriptionId) {
    subscrService.deleteSubscription(subscriptionId);
    return ApiResponse.onSuccess();
  }
}
