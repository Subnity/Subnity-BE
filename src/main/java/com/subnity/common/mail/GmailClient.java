package com.subnity.common.mail;

import com.subnity.common.config.FeignClientConfig;
import com.subnity.common.mail.response.GmailList;
import com.subnity.common.mail.response.Gmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Gmail API Client
 */
@FeignClient(
  name = "gmail-client",
  url = "https://gmail.googleapis.com/gmail/v1/users/me",
  configuration = FeignClientConfig.class
)
public interface GmailClient {

  /**
   * 키워드 기반으로 사용자의 메일에서 메일 목록을 반환하는 메서드
   * @param token : OAuth2 Access Token
   * @param query : 검색하고 싶은 키워드
   * @param maxResults : 최대 결과
   * @return : 메일 목록을 반환
   */
  @GetMapping(value = "/messages")
  GmailList getGmailList(
    @RequestHeader(value = "Authorization") String token,
    @RequestParam(value = "q") String query,
    @RequestParam(value = "maxResults") int maxResults
  );

  /**
   * 특정 메일을 반환하는 메서드
   * @param token : OAuth2 Access Token
   * @param mailId : 검색하고 싶은 메일 ID
   * @return : 특정 메일을 반환
   */
  @GetMapping(value = "/messages/{id}")
  Gmail getGmailById(
    @RequestHeader(value = "Authorization") String token,
    @PathVariable(value = "id") String mailId
  );
}
