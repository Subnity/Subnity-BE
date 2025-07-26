package com.subnity.domain.monthly_report.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.monthly_report.controller.response.GetCategoryCostDto;
import com.subnity.domain.monthly_report.controller.response.GetMonthlyReportResponse;
import com.subnity.domain.payment_history.controller.response.PaymentCategoryCostDto;
import com.subnity.domain.payment_history.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * MonthlyReportService : 월간 리포트 관련 Service
 */
@Service
@RequiredArgsConstructor
public class MonthlyReportService {

  private final DecimalFormat formatter = new DecimalFormat("#,###");
  private final PaymentHistoryRepository paymentHistoryRepository;

  /**
   * 월간 리포트 조회
   * @return : 월간 리포트 조회 응답 객체 반환
   */
  public GetMonthlyReportResponse getMonthlyReport(LocalDate date) {
    List<GetCategoryCostDto> categoryCostList = new ArrayList<>();
    AtomicLong totalPaymentCost = new AtomicLong();

    // 카테고리별 지출 목록
    List<PaymentCategoryCostDto> paymentCategoryCostDto = this.paymentHistoryRepository.paymentCategoryCostByMemberIdAndDate(SecurityUtils.getAuthMemberId(), date);
    if (paymentCategoryCostDto.isEmpty()) throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "해당 날짜에는 결제된 구독이 없습니다.");

    paymentCategoryCostDto.forEach(payment -> {
      totalPaymentCost.addAndGet(payment.getTotalCost());
      categoryCostList.add(
        GetCategoryCostDto.builder()
          .totalCost(formatter.format(payment.getTotalCost()))
          .category(payment.getCategory())
          .build()
      );
    });

    return GetMonthlyReportResponse.builder()
      .year(date.getYear())
      .month(date.getMonthValue())
      .totalPayment(formatter.format(totalPaymentCost.get()))
      .categoryCostList(categoryCostList)
      .build();
  }
}
