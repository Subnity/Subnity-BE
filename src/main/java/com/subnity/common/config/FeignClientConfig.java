package com.subnity.common.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

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
      requestTemplate.header("Content-Type", "application/json");
      requestTemplate.header("Accept", "application/json");
    };
  }
}
