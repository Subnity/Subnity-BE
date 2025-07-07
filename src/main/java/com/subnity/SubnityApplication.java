package com.subnity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SubnityApplication {
  public static void main(String[] args) {
    SpringApplication.run(SubnityApplication.class, args);
  }
}
