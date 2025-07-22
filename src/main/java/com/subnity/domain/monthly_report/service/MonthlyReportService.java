package com.subnity.domain.monthly_report.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.monthly_report.CategoryCost;
import com.subnity.domain.monthly_report.MonthlyReport;
import com.subnity.domain.monthly_report.controller.response.GetMonthlyReportResponse;
import com.subnity.domain.monthly_report.repository.JpaCategoryExpenseRepository;
import com.subnity.domain.monthly_report.repository.JpaMonthlyReportRepository;
import com.subnity.domain.monthly_report.repository.MonthlyReportRepository;
import com.subnity.domain.payment_history.controller.response.GetPaymentHistoryResponse;
import com.subnity.domain.payment_history.service.PaymentHistoryService;
import com.subnity.domain.subscription.controller.response.GetActiveSubscrDto;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MonthlyReportService {

  private final JpaMonthlyReportRepository jpaMonthlyReportRepository;
  private final JpaCategoryExpenseRepository jpaCategoryExpenseRepository;
  private final MonthlyReportRepository monthlyReportRepository;
  private final SubscrRepository subscrRepository;

  private final Map<String, Integer> categoryCost = new HashMap<>();
  private final Map<String, Integer> categoryRatio = new HashMap<>();

  private final PaymentHistoryService paymentHistoryService;

  /**
   * 구독 월간 리포트 생성 메서드
   * (로직 생각하느라 오래 걸렸네ㅠㅠ)
   *
   * 로직에 문제가 있는것 같아 잠시 생각하고(쉬고) 다시 ㄱㄱ
   * 결제 히스토리를 조회해서 기록이 있다면 그걸 총 금액으로 계산해야함..!! (해결)
   */
  public void createMonthlyReport() {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    int month = LocalDateTime.now().getMonthValue();
    GetMonthlyReportResponse monthlyReportResponse = this.monthlyReportRepository.getMonthlyReportByMonth(memberId, month);

    // 이미 해당 월에 리포트가 있다면 예외 던짐 (똑같은 월에 추가하지 못하도록)
    if (monthlyReportResponse != null) throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 " + month + "월 리포트가 등록되어 있습니다.");

    // MonthlyReport의 categoryExpenseList에 넣기 위함
    List<CategoryCost> categoryCostList = new ArrayList<>();

    // 1. 활성화된 구독 목록 조회
    List<GetActiveSubscrDto> activeSubscrList = this.subscrRepository.activeTotalSubscrCount();

    // 1-1. 구독 중인 목록이 없을 경우 예외 발생
    if (activeSubscrList.isEmpty()) throw new GeneralException(ErrorStatus.NOT_FOUND, "구독 중인 목록을 찾을 수 없습니다.");

    // 2. 실제 결제가 일어난 활성화된 구독에서 카테고리별 구독 금액 계산하기
    List<GetPaymentHistoryResponse> paymentHistoryList = paymentHistoryService.getPaymentHistoryList();
    paymentHistoryList.forEach(paymentHistory ->
      activeSubscrList.forEach(subscr ->
        Stream.of(SubscrCategory.values())
          .filter(category -> category.equals(subscr.getCategory()) && subscr.getSubscrId() == paymentHistory.getSubscrId())
          .forEach(category -> calculateCategoryCost(subscr))
      )
    );

    // 3. MonthlyReport를 먼저 저장하기
    LocalDateTime now = LocalDateTime.now();
    MonthlyReport monthlyReport = MonthlyReport.builder()
      .year(now.getYear())
      .month(now.getMonthValue())
      .createdAt(now)
      .member(member)
      .build();
    this.jpaMonthlyReportRepository.save(monthlyReport);

    // 4. 활성화된 구독에서 카테고리별 비율 계산하기
    int categoryTotalCount = categoryRatio.values()
      .stream()
      .mapToInt(value -> value)
      .sum();

    Stream.of(SubscrCategory.values())
      .forEach(category -> categoryRatio.keySet().stream().filter(key -> key.equals(category.name()))
          .forEach(key -> {
            // 카테고리별 비율 (백분율)
            int ratio = categoryRatio.get(key) * 100 / categoryTotalCount;

            // 5. CategoryCost 리스트에 추가 및 저장
            CategoryCost categoryCostEntity = CategoryCost.builder()
              .monthlyReport(monthlyReport)
              .totalCost(categoryCost.get(key))
              .category(SubscrCategory.valueOf(key))
              .ratio(ratio)
              .build();
            this.jpaCategoryExpenseRepository.save(categoryCostEntity);
            categoryCostList.add(categoryCostEntity);
          })
      );

    // 6. categoryExpenseList 넣어 다시 저장하기 (수정)
    monthlyReport.toBuilder()
      .categoryExpenseList(categoryCostList)
      .build();
  }

  /**
   * 카테고리별 결제 금액과 비율 계산하는 메서드
   * @param subscrDto : 활성화된 구독 정보 Dto 객체
   */
  private void calculateCategoryCost(GetActiveSubscrDto subscrDto) {
    // int형으로 받으면 수정 해야할 듯 (확실히 금액을 String으로 받으니까 불편해;;)
    int cost = Integer.parseInt(subscrDto.getCost().replace(",", ""));

    if (!this.categoryCost.containsKey(subscrDto.getCategory().name())) {
      this.categoryCost.put(subscrDto.getCategory().name(), cost);
      this.categoryRatio.put(subscrDto.getCategory().name(), 1);
    } else {
      this.categoryCost.put(
        subscrDto.getCategory().name(),
        this.categoryCost.get(subscrDto.getCategory().name()) + cost
      );

      this.categoryRatio.put(
        subscrDto.getCategory().name(),
        this.categoryRatio.get(subscrDto.getCategory().name()) + 1
      );
    }
  }
}
