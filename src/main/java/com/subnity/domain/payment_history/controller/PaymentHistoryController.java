package com.subnity.domain.payment_history.controller;

import com.subnity.domain.payment_history.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
@Tag(name = "Payment History", description = "결제 히스토리 관련 API")
public class PaymentHistoryController {
  private final PaymentHistoryService paymentHistoryService;
}
