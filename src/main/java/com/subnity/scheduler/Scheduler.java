package com.subnity.scheduler;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.common.mail.utils.MailUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.domain.subscription.enums.PaymentCycle;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class Scheduler {

  private final ThreadPoolTaskScheduler taskScheduler;
  private final Map<String, ScheduledFuture<?>> scheduledFutures = new HashMap<>();

  @PostConstruct
  public void init() {
    taskScheduler.initialize();
  }

  public void mailSearchTaskRegister(String id, String cron, Member member, SearchMailDto dto) {
    ScheduledFuture<?> future = taskScheduler.schedule(() -> {
      List<SearchMail> mails = MailUtils.mailSearchByKeyword(
        SearchMailDto.builder()
          .keyword(dto.getKeyword())
          .cycle(dto.getCycle())
          .build(),
        member
      );

      mails.forEach(mail -> {
        System.out.println(mail.toString());

        /*
        결제 히스토리 등록 로직 구현하면 될듯?
         */
      });
    }, new CronTrigger(cron, ZoneId.of("Asia/Seoul")));

    scheduledFutures.put(id, future);
  }

  public void removeCronTask(String id) {
    ScheduledFuture<?> future = scheduledFutures.get(id);
    if (future != null) {
      future.cancel(false);
    }
  }
}
