package com.subnity.common.mail.utils;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.mail.GmailClient;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.common.mail.dto.SearchMailDto;
import com.subnity.common.mail.response.SearchMail;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.common.mail.response.Gmail;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MailUtils : 메일 관련 로직을 모아둔 유틸 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MailUtils {

  @Value("${mail.from}")
  private String nonFromMail;
  private final JavaMailSender nonMailSender;
  private final GmailClient nonGmailClient;

  private static String fromMail;
  private static JavaMailSender mailSender;
  private static GmailClient gmailClient;

  @PostConstruct
  public void init() {
    fromMail = this.nonFromMail;
    mailSender = this.nonMailSender;
    gmailClient = this.nonGmailClient;
  }

  /**
   * 메일 발송 메서드
   * @param text : 발송하고 싶은 메시지
   */
  public static void sendMail(String text) {
    Member member = getAuthMember();
    SimpleMailMessage message = new SimpleMailMessage();

    message.setText(text);
    message.setSubject("Subnity에서 안내드립니다.");
    message.setFrom(fromMail);
    message.setTo(member.getMail());

    mailSender.send(message);
  }

  /**
   * Gmail API를 사용해서 키워드 기반으로 사용자의 특정 메일을 조회하는 메서드
   * @param dto : 메일 정보 Dto 객체
   * @return : 검색한 메일 목록을 반환
   */
  public static List<SearchMail> mailSearchByKeyword(SearchMailDto dto, Member member) {
    List<SearchMail> searchMailList = new ArrayList<>();

    String mailToken = "Bearer " + member.getMailToken();
    LocalDate now = LocalDate.now();

    String query = "subject:" + dto.getKeyword() + " 결제내역";
    List<Map<String, String>> mailList = gmailClient.getGmailList(mailToken, query, 20).getMessages();

    if (mailList != null && !mailList.isEmpty()) { // 메일 목록이 비어있지 않으면 아래 로직 실행
      for (Map<String, String> mail : mailList) {
        Gmail data = gmailClient.getGmailById(mailToken, mail.get("id"));

        Map<String, Object> body = data.getPayload().getBody();
        int bodySize = (int) body.get("size");

        // bodySize가 0이면 데이터가 들어있지 않은걸로 간주
        if (bodySize != 0) {
          String amount = paymentAmount(body);
          LocalDate date = paymentDate(body);

          if (amount != null && date != null) {
            switch (dto.getCycle()) {
              case MONTH -> {
                if (date.getMonthValue() == now.getMonthValue()) {
                  searchMailList.add(
                    SearchMail.builder()
                      .cost(amount)
                      .paymentDate(date)
                      .build()
                  );
                }
              }
              case YEAR -> {
                if (date.getYear() == now.getYear()) {
                  searchMailList.add(
                    SearchMail.builder()
                      .cost(amount)
                      .paymentDate(date)
                      .build()
                  );
                }
              }
            }
          }
        } else {
          log.error("데이터가 없습니다.");
        }
      }
    } else {
      return searchMailList;
    }

    return searchMailList;
  }

  /**
   * Base64로 인코딩된 html 문서를 디코딩하고 element 목록을 반환하는 메서드
   * @param body : Gmail API body 부분
   * @return : html 문서의 element 목록을 반환
   */
  private static Elements htmlElements(Map<String, Object> body) {
    String encondedHtml = body.get("data").toString();
    byte[] decodedHtmlBytes = Base64.getUrlDecoder().decode(encondedHtml);
    String html = new String(decodedHtmlBytes);

    Document document = Jsoup.parse(html); // html 문서로 변환
    return document.getAllElements(); // 모든 element 목록을 반환
  }

  /**
   * html 문서에서 결제 정보를 담고 있는 element를 찾아 결제 금액을 얻는 메서드
   * @param body : Gmail API body 부분
   * @return : 결제 금액을 반환
   */
  private static String paymentAmount(Map<String, Object> body) {
    String responseAmount = null;

    // 결제 금액 찾기
    for (Element element : htmlElements(body)) {
      Pattern pattern = Pattern.compile("\\d{1,3}(?:,\\d{3})+"); // 금액(1,000 or 200,000) 정규식
      Matcher matcher = pattern.matcher(element.text());

      if (matcher.find()) {
        responseAmount = matcher.group();
      }
    }

    return responseAmount;
  }

  /**
   * html 문서에서 결제 날짜를 찾아 반환하는 메서드
   * @param body : Gmail API body 부분
   * @return : 결제 날짜를 반환
   */
  private static LocalDate paymentDate(Map<String, Object> body) {
    LocalDate responseDate = null;

    // 결제 날짜 찾기
    for (Element element : htmlElements(body)) {
      Pattern pattern = Pattern.compile("\\d{4}년\\s\\d{2}월\\s\\d{2}일");
      Matcher matcher = pattern.matcher(element.text());

      if (matcher.find()) {
        String matchDate = matcher.group();

        int year = Integer.parseInt(
          matchDate.substring(0, matchDate.indexOf("년"))
        );
        int month = Integer.parseInt(
          matchDate.substring(matchDate.indexOf("년") + 2, matchDate.indexOf("월"))
        );
        int day = Integer.parseInt(
          matchDate.substring(matchDate.indexOf("월") + 2, matchDate.indexOf("일"))
        );

        responseDate = LocalDate.of(year, month, day);
      }
    }

    return responseDate;
  }

  /**
   * 인증된 사용자를 얻는 메서드
   * @return : 인증된 사용자를 반환
   */
  private static Member getAuthMember() {
    String memberId = SecurityUtils.getAuthMemberId();
    return MemberUtils.getMember(memberId);
  }
}
