package com.subnity.domain.test.service;

import com.subnity.domain.test.TestEntity;
import com.subnity.domain.test.controller.request.CreateTestRequest;
import com.subnity.domain.test.repository.CreateTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTestService {
  private final CreateTestRepository createTestRepository;

  public void createTest(CreateTestRequest request) {
    createTestRepository.save(
      TestEntity.builder()
        .title(request.title())
        .content(request.content())
        .build()
    );
  }
}
