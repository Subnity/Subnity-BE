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

/**
 * SubscrController : 구독 관련 Controller
 */
@RestController
@RequestMapping(value = "/subscr")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "구독 관련 API")
public class SubscrController {
  private final SubscrService subscrService;

  /**
   * 구독 생성 엔드포인트
   * @param request : CreateSubscrRequest 객체 (새로운 구독을 생성할때 사용하는 객체)
   * @return : 공통 응답 객체 반환 | 예외시 공통 예외 반환
   */
  @PostMapping(value = "/create")
  @Operation(summary = "구독 생성", description = "구독 생성 엔드포인트")
  public ApiResponse<Void> createSubscr(@RequestBody @Valid CreateSubscrRequest request) {
    subscrService.createSubscr(request);
    return ApiResponse.onSuccess();
  }

  /**
   * 특정 구독 조회 엔드포인트
   * @param subscrId : 구독 ID
   * @return : 구독 조회 응답 객체 반환
   */
  @GetMapping(value = "/{id}")
  @Operation(summary = "구독 정보 조회", description = "구독 ID를 통해서 하나의 구독 정보를 가져오는 엔드포인트")
  public ApiResponse<GetSubscrResponse> getSubscr(@PathVariable("id") String subscrId) {
    return ApiResponse.onSuccess(subscrService.getSubscr(subscrId));
  }

  /**
   * 구독 목록 조회 엔드포인트
   * @return : 구독 목록 조회 응답 객체 반환
   */
  @GetMapping(value = "/list")
  @Operation(summary = "구독 목록 조회", description = "구독 목록 조회 엔드포인트")
  public ApiResponse<List<GetSubscrResponse>> getSubscrList() {
    return ApiResponse.onSuccess(subscrService.getSubscrList());
  }

  /**
   * 특정 구독 수정 엔드포인트
   * @param request : UpdateSubscrRequest 객체 (구독 수정 요청 객체)
   * @return : 공통 응답 객체 반환
   */
  @PatchMapping(value = "/update")
  @Operation(summary = "구독 정보 수정", description = "구독 정보 수정 엔드포인트")
  public ApiResponse<Void> updateSubscr(@RequestBody @Valid UpdateSubscrRequest request) {
    subscrService.updateSubscr(request);
    return ApiResponse.onSuccess();
  }

  /**
   * 특정 구독 제거 엔드포인트
   * @param subscrId : 구독 ID
   * @return : 공통 응답 객체 반환
   */
  @DeleteMapping(value = "/delete/{id}")
  @Operation(summary = "구독 제거", description = "구독 정보를 제거하는 엔드포인트")
  public ApiResponse<Void> deleteSubscr(@PathVariable("id") String subscrId) {
    subscrService.deleteSubscr(subscrId);
    return ApiResponse.onSuccess();
  }
}
