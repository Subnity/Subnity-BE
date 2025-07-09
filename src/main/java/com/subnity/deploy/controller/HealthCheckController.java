package com.subnity.deploy.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {

  @GetMapping(value = "/health")
  public String healthCheck() {
    return "OK";
  }
}
