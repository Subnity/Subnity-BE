package com.subnity.domain.test.service;

import com.subnity.domain.test.TestEntity;
import com.subnity.domain.test.controller.response.GetTestResponse;
import com.subnity.domain.test.repository.DetailTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailTestService {
  private final DetailTestRepository getTestRepository;

  public GetTestResponse getTest(String id) {
    TestEntity testEntity = getTestRepository.getTestEntity(Long.parseLong(id));
    return GetTestResponse.builder()
      .id(testEntity.getId())
      .title(testEntity.getTitle())
      .content(testEntity.getContent())
      .build();
  }
}
