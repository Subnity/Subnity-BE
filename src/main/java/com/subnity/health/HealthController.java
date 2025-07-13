package com.subnity.health;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AWS Elastic Beanstalk Health Check를 위한 Controller
 */
@Hidden
@RestController
public class HealthController {

  /**
   * Health Check를 위한 메서드
   * @return : "OK" 반환
   */
  @GetMapping(value = "/health")
  public String healthCheck() {
    return "OK";
  }
}
