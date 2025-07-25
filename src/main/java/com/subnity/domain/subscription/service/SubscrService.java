package com.subnity.domain.subscription.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.subscription.Subscription;
import com.subnity.domain.subscription.controller.request.CreateSubscrRequest;
import com.subnity.domain.subscription.controller.request.UpdateSubscrRequest;
import com.subnity.domain.subscription.controller.response.GetSubscrResponse;
import com.subnity.domain.subscription.enums.PaymentCycle;
import com.subnity.domain.subscription.repository.JpaSubscrRepository;
import com.subnity.domain.subscription.repository.SubscrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * SubscrService : 구독 관련 Service
 */
@Service
@RequiredArgsConstructor
public class SubscrService {
  /* 31일이 있는 달 */
  private final String[] MONTHS = { "01", "03", "05", "07", "08", "10", "12" };

  private final JpaSubscrRepository jpaRepository;
  private final SubscrRepository subscrRepository;

  /**
   * 구독 생성 메서드
   * @param request : 구독 생성 요청 객체
   */
  public void createSubscr(CreateSubscrRequest request) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    if (member != null) {
      this.jpaRepository.save(
        Subscription.builder()
          .platformName(request.platformName())
          .description(request.description())
          .cost(Long.parseLong(request.cost().replace(",", "")))
          .status(request.status())
          .category(request.category())
          .isNotification(request.isNotification())
          .paymentCycle(request.paymentCycle())
          .lastPaymentDate(request.lastPaymentDate())
          .nextPaymentDate(
            this.getNextPaymentDate(request.lastPaymentDate(), request.paymentCycle())
          )
          .member(member)
          .build()
      );
    } else {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독을 생성할 수 없습니다.");
    }
  }

  /**
   * 특정 구독 조회 메서드
   * @param subscrId : 구독 ID
   * @return : 구독 정보 반환
   */
  public GetSubscrResponse getSubscr(String subscrId) {
    String memberId = SecurityUtils.getAuthMemberId();
    Subscription subscrEntity = this.subscrRepository.findBySubscrId(Long.parseLong(subscrId), memberId);

    if (subscrEntity == null) throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독을 찾을 수 없습니다.");

    return Subscription.from(subscrEntity);
  }

  /**
   * 구독 목록 조회 메서드
   * @return : 구독 목록 반환
   */
  public List<GetSubscrResponse> getSubscrList() {
    String memberId = SecurityUtils.getAuthMemberId();
    List<Subscription> subscrEntityList = this.subscrRepository.subscrListByMemberId(memberId);

    if (subscrEntityList.isEmpty()) throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "구독 목록을 찾을 수 없습니다.");

    List<GetSubscrResponse> subscrList = new ArrayList<>();
    subscrEntityList.forEach(subscr -> subscrList.add(Subscription.from(subscr)));

    return subscrList;
  }

  /**
   * 구독 수정 메서드
   * @param request : 구독 수정 요청 객체
   */
  public void updateSubscr(UpdateSubscrRequest request) {
    String memberId = SecurityUtils.getAuthMemberId();
    this.subscrRepository.updateSubscr(request, memberId);
  }

  /**
   * 구독 제거 메서드
   * @param subscrId : 구독 ID
   */
  public void deleteSubscr(String subscrId) {
    this.jpaRepository.deleteById(Long.parseLong(subscrId));
  }

  /**
   * 다음 구독 결제일 추측 메서드
   * @param date : 구독일
   * @param paymentCycle : 결제 주기
   * @return : 다음 결제일 반환
   */
  private LocalDate getNextPaymentDate(LocalDate date, PaymentCycle paymentCycle) {
    LocalDate nextPaymentDate = null;

    switch (paymentCycle) {
      case MONTH:
        String lastMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        for (String m : MONTHS) {
          if (lastMonth.equals(m)) { // 31이 있는 달
            /*
              31일에서 +1하는 이유는 스케줄러 때문입니다.
              만약 결제일 밤 23시에 스케줄러가 돌아간다면 그 뒤 1시간동안 결제된 메일을 검색할 수 없기 때문에 00시에 전날(결제일) 메일을 검색할 수 있도록 31일에서 +1을 했습니다.
             */
            nextPaymentDate = date.plusDays(32);
            return nextPaymentDate;
          }
        }
        nextPaymentDate = date.plusDays(31);
        break;
      case YEAR:
        nextPaymentDate = date.plusYears(1);
        break;
    }

    return nextPaymentDate;
  }
}
