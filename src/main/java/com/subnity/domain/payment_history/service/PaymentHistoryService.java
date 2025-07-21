package com.subnity.domain.payment_history.service;

import com.subnity.domain.payment_history.controller.request.CreatePaymentHistoryRequest;
import com.subnity.domain.payment_history.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {
  private final PaymentHistoryRepository paymentHistoryRepository;

  public void createPaymentHistory(CreatePaymentHistoryRequest request) {
    paymentHistoryRepository.save(request, LocalDateTime.now());
  }
}
