package com.subnity.domain.enum_api.service;

import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.common.utils.enums.SubscrStatus;
import com.subnity.domain.payment_history.enums.PaymentStatus;
import com.subnity.domain.subscription.enums.PaymentCycle;
import com.subnity.domain.enum_api.controller.response.ListEnumDto;
import com.subnity.domain.enum_api.controller.response.ListEnumResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * EnumService : Enum 관련 Service
 */
@Service
public class EnumService {

  /**
   * SubscrCategory Enum 목록 조회
   * @return : SubscrCategory Enum 응답 객체 반환
   */
  public ListEnumResponse getSubscrCategoryEnumList() {
    List<ListEnumDto> enumList = Stream.of(SubscrCategory.values())
      .map(e ->
        ListEnumDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return ListEnumResponse.builder()
      .enumList(enumList)
      .build();
  }

  /**
   * PaymentCycle Enum 목록 조회
   * @return : PaymentCycle Enum 응답 객체 반환
   */
  public ListEnumResponse getPaymentCycleEnumList() {
    List<ListEnumDto> enumList = Stream.of(PaymentCycle.values())
      .map(e ->
        ListEnumDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return ListEnumResponse.builder()
      .enumList(enumList)
      .build();
  }

  /**
   * SubscrStatus Enum 목록 조회
   * @return : SubscrStatus Enum 응답 객체 반환
   */
  public ListEnumResponse getSubscrStatusEnumList() {
    List<ListEnumDto> enumList = Stream.of(SubscrStatus.values())
      .map(e ->
        ListEnumDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return ListEnumResponse.builder()
      .enumList(enumList)
      .build();
  }

  /**
   * PaymentStatus Enum 목록 조회
   * @return : PaymentStatus Enum 응답 객체 반환
   */
  public ListEnumResponse getPaymentStatusEnumList() {
    List<ListEnumDto> enumList = Stream.of(PaymentStatus.values())
      .map(e ->
        ListEnumDto.builder()
          .code(e.name())
          .value(e.getValue())
          .build()
      ).toList();

    return ListEnumResponse.builder()
      .enumList(enumList)
      .build();
  }
}
