package com.subnity.common.mail;

import com.subnity.common.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * Google API Client
 */
@FeignClient(
  name = "google-client",
  url = "https://oauth2.googleapis.com",
  configuration = FeignClientConfig.class
)
public interface GoogleClient {

  /**
   * Access Token 재발급 API 호출 메서드
   * @param body : 본문
   * @return : Access Token이 담겨있는 Json 반환
   */
  @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  Map<String, Object> recreateAccessToken(MultiValueMap<String, String> body);
}
