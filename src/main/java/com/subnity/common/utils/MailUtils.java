package com.subnity.common.utils;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * MailUtils : 메일 관련 로직을 모아둔 유틸 클래스
 */
@Component
@RequiredArgsConstructor
public class MailUtils {

  @Value("${mail.from}")
  private String from;
  private final JavaMailSender mailSender;

  private static JavaMailSender sender;
  private static String mailFrom;

  @PostConstruct
  private void init() {
    sender = this.mailSender;
    mailFrom = this.from;
  }

  /**
   * 메일 발송 메서드
   * @param text : 보내고 싶은 메시지
   */
  public static void send(String text) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setText(text);
    message.setFrom(mailFrom);
    message.setTo(member.getMail());
    sender.send(message);
  }
}
