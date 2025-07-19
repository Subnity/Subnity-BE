package com.subnity.mail.service;

import com.subnity.auth.utils.SecurityUtils;
import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import com.subnity.domain.member.Member;
import com.subnity.domain.member.utils.MemberUtils;
import com.subnity.mail.GmailClient;
import com.subnity.mail.controller.response.GetGmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MailService : 메일 관련 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
  private final GmailClient gmailClient;

  public void mailSearchByKeyword(String keyword) {
    String memberId = SecurityUtils.getAuthMemberId();
    Member member = MemberUtils.getMember(memberId);

    String gmailToken = "Bearer " + member.getMailToken();
    String query = "subject:" + keyword + "결제내역 OR subject:" + keyword;

    List<Map<String, String>> mailList = gmailClient.getGmailList(gmailToken, query, 100).getMessages();

    if (!mailList.isEmpty()) { // 메일 목록이 비어있지 않으면 아래 로직 실행
      for (Map<String, String> mail : mailList) {
        GetGmailResponse data = gmailClient.getGmailById(gmailToken, mail.get("id"));

        Map<String, Object> body = data.getPayload().getBody();
        int bodySize = (int) body.get("size");

        // bodySize가 0이면 데이터가 들어있지 않은걸로 간주
        if (bodySize != 0) {
          String amount = paymentAmount(body);
        } else {
          log.error("데이터가 없습니다.");
        }
      }
    } else {
      throw new GeneralException(ErrorStatus.NOT_FOUND, "메일을 찾을 수 없습니다.");
    }
  }

  private Elements htmlElements(Map<String, Object> body) {
    String encondedHtml = body.get("data").toString();
    byte[] decodedHtmlBytes = Base64.getUrlDecoder().decode(encondedHtml);
    String html = new String(decodedHtmlBytes);

    Document document = Jsoup.parse(html); // html 문서로 변환
    return document.getAllElements(); // 모든 element 목록을 반환
  }

  private String paymentAmount(Map<String, Object> body) {
    String responseAmount = null;

    // 결제 금액 찾기
    for (Element element : htmlElements(body)) {
      Pattern pattern = Pattern.compile("\\d{1,3}(?:,\\d{3})+"); // 금액(1,000 or 200,000) 정규식
      Matcher matcher = pattern.matcher(element.text());

      if (matcher.find()) {
        responseAmount = matcher.group();
        log.info("결제 금액: {}원", matcher.group());
      }
    }

    return responseAmount;
  }
}
