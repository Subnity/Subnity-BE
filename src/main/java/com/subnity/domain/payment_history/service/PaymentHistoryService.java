package com.subnity.domain.payment_history.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.payment_history.PaymentHistory;
import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.controller.response.GetPaymentHistoryResponse;
import com.subnity.domain.payment_history.repository.JpaPaymentHistoryRepository;
import com.subnity.domain.payment_history.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentHistoryService : 결제 히스토리 관련 Service
 */
@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

  private final PaymentHistoryRepository paymentHistoryRepository;
  private final JpaPaymentHistoryRepository jpaPaymentHistoryRepository;

  /**
   * 결제 히스토리 등록 메서드
   * @param request : 결제 히스토리 생성 요청 객체
   */
  public void createPaymentHistory(CreatePaymentHistoryRequest request) {
    this.paymentHistoryRepository.save(request, LocalDateTime.now());
  }

  /**
   * 특정 결제 히스토리 조회 메서드
   * @param paymentHistoryId : 결제 히스토리 ID
   * @return : 결제 히스토리 조회 응답 객체 반환
   */
  public GetPaymentHistoryResponse getPaymentHistory(String paymentHistoryId) {
    PaymentHistory paymentHistory = this.paymentHistoryRepository.paymentHistoryById(Long.parseLong(paymentHistoryId));
    return PaymentHistory.from(paymentHistory);
  }

  /**
   * 결제 히스토리 목록 조회 메서드
   * @return : 결제 히스토리 목록 조회 응답 객체 반환
   */
  public List<GetPaymentHistoryResponse> getPaymentHistoryList() {
    String memberId = SecurityUtils.getAuthMemberId();
    List<PaymentHistory> paymentHistoryList = this.paymentHistoryRepository.paymentHistoryListByMemberId(memberId);

    List<GetPaymentHistoryResponse> paymentHistoryResponseList = new ArrayList<>();
    paymentHistoryList.forEach(paymentHistory -> paymentHistoryResponseList.add(PaymentHistory.from(paymentHistory)));

    return paymentHistoryResponseList;
  }

  /**
   * 특정 구독 결제 히스토리 목록 조회 메서드
   * @param subscrId : 구독 ID
   * @return : 결제 히스토리 목록 조회 응답 객체 반환
   */
  public List<GetPaymentHistoryResponse> getPaymentHistoryListBySubscrId(String subscrId) {
    String memberId = SecurityUtils.getAuthMemberId();
    List<PaymentHistory> paymentHistoryList = this.paymentHistoryRepository.paymentHistoryListBySubscrId(Long.parseLong(subscrId), memberId);

    List<GetPaymentHistoryResponse> paymentHistoryResponseList = new ArrayList<>();
    paymentHistoryList.forEach(paymentHistory -> paymentHistoryResponseList.add(PaymentHistory.from(paymentHistory)));

    return paymentHistoryResponseList;
  }

  /**
   * 특정 결제 히스토리 삭제 메서드
   * @param paymentHistoryId : 결제 히스토리 ID
   */
  public void deletePaymentHistory(String paymentHistoryId) {
    this.jpaPaymentHistoryRepository.deleteById(Long.parseLong(paymentHistoryId));
  }
}
