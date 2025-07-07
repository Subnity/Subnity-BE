package com.subnity.domain.test.service;

import com.subnity.domain.test.controller.response.GetTestResponse;
import com.subnity.domain.test.repository.ListTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListTestService {
  private final ListTestRepository listTestRepository;

  public List<GetTestResponse> getTestList() {
    List<GetTestResponse> getTestResponseList = new ArrayList<>();

    listTestRepository.getTestEntityList().forEach(e -> {
      getTestResponseList.add(
        GetTestResponse.builder()
          .id(e.getId())
          .title(e.getTitle())
          .content(e.getContent())
          .build()
      );
    });

    return getTestResponseList;
  }
}
