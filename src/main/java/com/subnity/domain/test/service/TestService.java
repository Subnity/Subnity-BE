package com.subnity.domain.test.service;

import com.subnity.domain.test.controller.request.CreateTestRequest;
import com.subnity.domain.test.controller.response.GetTestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
  private final CreateTestService createTestService;
  private final DetailTestService detailTestService;
  private final ListTestService listTestService;

  public void createTest(CreateTestRequest request) {
    createTestService.createTest(request);
  }

  public GetTestResponse getTest(String id) {
    return detailTestService.getTest(id);
  }

  public List<GetTestResponse> getTestList() {
    return listTestService.getTestList();
  }
}
