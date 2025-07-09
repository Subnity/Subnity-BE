package com.subnity.enum_api.service;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.subscription.enums.PaymentCycle;
import com.subnity.enum_api.controller.response.GetEnumListDto;
import com.subnity.enum_api.controller.response.GetEnumResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class EnumService {

  /**
   * SubscrCategory Enum 목록 조회
   * @return : SubscrCategory Enum 응답 객체 반환
   */
  public GetEnumResponse getSubscrCategoryEnumList() {
    List<GetEnumListDto> enumList = Stream.of(SubscrCategory.values())
      .map(e ->
        GetEnumListDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return GetEnumResponse.builder()
      .enumList(enumList)
      .build();
  }

  /**
   * PaymentCycle Enum 목록 조회
   * @return : PaymentCycle Enum 응답 객체 반환
   */
  public GetEnumResponse getPaymentCycleEnumList() {
    List<GetEnumListDto> enumList = Stream.of(PaymentCycle.values())
      .map(e ->
        GetEnumListDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return GetEnumResponse.builder()
      .enumList(enumList)
      .build();
  }

  /**
   * SubscrStatus Enum 목록 조회
   * @return : SubscrStatus Enum 응답 객체 반환
   */
  public GetEnumResponse getSubscrStatusEnumList() {
    List<GetEnumListDto> enumList = Stream.of(SubscrStatus.values())
      .map(e ->
        GetEnumListDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return GetEnumResponse.builder()
      .enumList(enumList)
      .build();
  }
}
