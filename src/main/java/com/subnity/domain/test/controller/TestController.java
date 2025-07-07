package com.subnity.domain.test.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.test.controller.request.CreateTestRequest;
import com.subnity.domain.test.controller.response.GetTestResponse;
import com.subnity.domain.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor
@Tag(name = "Test", description = "Test 관련 API")
public class TestController {
  private final TestService testService;

  @PostMapping(value = "/create")
  @Operation(summary = "테스트 객체 생성", description = "테스트 객체를 생성할 때 사용하는 API")
  public ApiResponse<Void> createTest(@RequestBody CreateTestRequest request) {
    testService.createTest(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping(value = "")
  @Operation(summary = "테스트 객체 조회", description = "테스트 객체를 조회할 때 사용하는 API")
  public ApiResponse<GetTestResponse> getTest(@RequestParam String id) {
    return ApiResponse.onSuccess(testService.getTest(id));
  }

  @GetMapping(value = "/list")
  @Operation(summary = "테스트 객체 목록 조회", description = "테스트 객체 목록을 조회할 때 사용하는 API")
  public ApiResponse<List<GetTestResponse>> getTestList() {
    return ApiResponse.onSuccess(testService.getTestList());
  }
}
