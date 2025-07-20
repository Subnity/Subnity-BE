package com.subnity.scheduler;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.common.mail.utils.MailUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.service.MemberService;
import com.subnity.domain.member.utils.MemberUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class Scheduler {

  private final Map<String, ScheduledFuture<?>> scheduledFutures = new HashMap<>();
  private final ThreadPoolTaskScheduler taskScheduler;
  private final MemberService memberService;

  @PostConstruct
  public void init() {
    taskScheduler.initialize();
  }

  /**
   * 메일 검색을 Scheduler에 등록하는 메서드
   * @param id : Scheduler ID
   * @param cron : Scheduler Cron
   * @param year : 년도
   * @param dto : 검색하고 싶은 메일 정보를 담고 있는 Dto
   */
  public void mailSearchTaskRegister(String id, String cron, int year, SearchMailDto dto) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);
    memberService.updateSchedulerId(id); // Scheduler ID 등록 & 수정

    ScheduledFuture<?> future = taskScheduler.schedule(() -> {
      if (Year.now().getValue() == year) {
        List<SearchMail> mails = MailUtils.mailSearchByKeyword(
          SearchMailDto.builder()
            .keyword(dto.getKeyword())
            .cycle(dto.getCycle())
            .build(),
          member
        );

        if (!mails.isEmpty()) {
          mails.forEach(mail -> {
            System.out.println(mail.toString());
          });
        }
      }

      this.removeCronTask(id); // 모든 일이 끝나면 다음에는 작업하지 않도록 해당 작업을 삭제
    }, new CronTrigger(cron, ZoneId.of("Asia/Seoul")));

    scheduledFutures.put(id, future);
  }

  /**
   * 작업을 다음에도 실행하지 않도록 하기 위한 작업 삭제 메서드
   * @param id : Scheduler ID
   */
  public void removeCronTask(String id) {
    ScheduledFuture<?> future = scheduledFutures.get(id);
    if (future != null) {
      future.cancel(false);
      scheduledFutures.remove(id);
    }
  }
}
