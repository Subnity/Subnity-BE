package com.subnity.mail;

import com.subnity.common.config.FeignClientConfig;
import com.subnity.mail.controller.response.GetGmailListResponse;
import com.subnity.mail.controller.response.GetGmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
  name = "gmail-client",
  url = "https://gmail.googleapis.com/gmail/v1/users/me",
  configuration = FeignClientConfig.class
)
public interface GmailClient {

  @GetMapping(value = "/messages")
  GetGmailListResponse getGmailList(
    @RequestHeader(value = "Authorization") String token,
    @RequestParam(value = "q") String query,
    @RequestParam(value = "maxResults") int maxResults
  );

  @GetMapping(value = "/messages/{id}")
  GetGmailResponse getGmailById(
    @RequestHeader(value = "Authorization") String token,
    @PathVariable(value = "id") String mailId
  );
}
