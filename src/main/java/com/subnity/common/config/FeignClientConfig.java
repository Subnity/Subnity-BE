package com.subnity.common.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * FeignClientConfig : 외부 API 호출 설정 파일
 */
@Configuration
public class FeignClientConfig {

  @Bean
  Request.Options requestOptions() {
    return new Request.Options(15, TimeUnit.SECONDS, 15, TimeUnit.SECONDS, false);
  }

  @Bean
  Retryer retry() {
    return new Retryer.Default(500, 1000, 2);
  }

  @Bean
  System.Logger.Level feignLoggerLevel() {
    return System.Logger.Level.ALL;
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      if (requestTemplate.url().contains("gmail/v1/users/me")) {
        requestTemplate.header("Content-Type", "application/json");
        requestTemplate.header("Accept", "application/json");
      }
    };
  }
}
