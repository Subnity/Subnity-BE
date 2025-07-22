package com.subnity.domain.monthly_report.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.utils.enums.SubscrCategory;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.monthly_report.CategoryCost;
import com.subnity.domain.monthly_report.MonthlyReport;
import com.subnity.domain.monthly_report.controller.response.GetCategoryCostDto;
import com.subnity.domain.monthly_report.repository.JpaCategoryExpenseRepository;
import com.subnity.domain.monthly_report.repository.JpaMonthlyReportRepository;
import com.subnity.domain.subscription.controller.response.GetActiveSubscrDto;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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
  private final SubscrRepository subscrRepository;

  private final Map<String, Integer> categoryCost = new HashMap<>();
  private final Map<String, Integer> categoryRatio = new HashMap<>();

  /*
   매월 1일에 새로운 월 리포트 생성
   */
//  @Scheduled(cron = "0 0 0 1 * *")
  public void createMonthlyReport() {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    // 1. 활성화된 구독 목록 조회
    List<GetActiveSubscrDto> activeSubscrList = this.subscrRepository.activeTotalSubscrCount();

    // activeSubscrList가 비어 있으면 계산 건너뛰고 바로 종료
//    if (activeSubscrList.isEmpty()) return;

    // MonthlyReport에 categoryExpenseList에 넣기 위함
    List<CategoryCost> categoryCostList = new ArrayList<>();

    // 2. 활성화된 구독에서 카테고리별 구독 금액 계산하기 (기본값 0)
    activeSubscrList.forEach(subscr -> {
      Stream.of(SubscrCategory.values())
        .filter(category -> category.equals(subscr.getCategory()))
        .forEach(category -> calculateCategoryCost(subscr));
    });

    // 먼저 저장하기 ()
    LocalDateTime now = LocalDateTime.now();
    MonthlyReport monthlyReport = MonthlyReport.builder()
      .year(now.getYear())
      .month(now.getMonthValue())
      .createdAt(now)
      .build();
    this.jpaMonthlyReportRepository.save(monthlyReport);

    // 3. 활성화된 구독에서 카테고리별 비율 계산하기
    int categoryTotalCount = categoryRatio.values()
      .stream()
      .mapToInt(value -> value)
      .sum();

    Stream.of(SubscrCategory.values())
      .forEach(category -> {
        categoryRatio.keySet().stream()
          .filter(key -> key.equals(category.name()))
          .forEach(key -> {
            int ratio = categoryRatio.get(key) * 100 / categoryTotalCount;
            System.out.println(key + " 카테고리 비율: " + ratio + "%");

            /*
             여기서 GetCategoryDto 사용해야할 듯
             */

            DecimalFormat categoryTotalDF = new DecimalFormat("#,###");
            String categoryCost = categoryTotalDF.format(
              this.categoryCost.get(key)
            );

            CategoryCost categoryCostEntity = CategoryCost.builder()
              .monthlyReport(monthlyReport)
              .totalCost(categoryCost)
              .category(SubscrCategory.valueOf(key))
              .ratio(ratio)
              .build();
            categoryCostList.add(categoryCostEntity);

            this.jpaCategoryExpenseRepository.save(categoryCostEntity);
          });
      });

    monthlyReport.toBuilder()
      .categoryExpenseList(categoryCostList)
      .build();

    this.jpaMonthlyReportRepository.save(monthlyReport);
  }

  private void calculateCategoryCost(GetActiveSubscrDto subscrDto) {
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
