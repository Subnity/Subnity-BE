package com.subnity.domain.monthly_report.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.monthly_report.controller.response.GetCategoryCostDto;
import com.subnity.domain.monthly_report.controller.response.GetMonthlyReportResponse;
import com.subnity.domain.payment_history.controller.response.PaymentCategoryCostDto;
import com.subnity.domain.payment_history.controller.response.PaymentTotalCostDto;
import com.subnity.domain.payment_history.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    String memberId = SecurityUtils.getAuthMemberId();
    List<GetCategoryCostDto> categoryCostList = new ArrayList<>();

    // 총 지출
    PaymentTotalCostDto paymentTotalCostDto = this.paymentHistoryRepository.paymentTotalCostByMemberId(memberId, date);
    if (paymentTotalCostDto == null) throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "해당 날짜에는 결제된 금액이 없습니다.");

    // 카테고리별 지출 목록
    List<PaymentCategoryCostDto> paymentCategoryCostDto = this.paymentHistoryRepository.paymentCategoryCostByMemberId(memberId, date);
    paymentCategoryCostDto.forEach(payment ->
      categoryCostList.add(
        GetCategoryCostDto.builder()
          .totalCost(formatter.format(payment.getTotalCost()))
          .category(payment.getCategory())
          .ratio((int) ((payment.getTotalCost() * 100.0) / paymentTotalCostDto.getTotalCost()))
          .build()
      )
    );

    return GetMonthlyReportResponse.builder()
      .year(date.getYear())
      .month(date.getMonthValue())
      .totalPayment(formatter.format(paymentTotalCostDto.getTotalCost()))
      .categoryCostList(categoryCostList)
      .build();
  }
}
