package com.subnity.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;

@Configuration
public class FeignConfig {

  @Bean
  public Level getLogLevel() {
    return Level.ALL;
  }
}
