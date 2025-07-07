package com.subnity.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOriginPatterns("*")
      .allowedMethods(
        HttpMethod.GET.name(), HttpMethod.POST.name(),
        HttpMethod.PATCH.name(), HttpMethod.PUT.name(),
        HttpMethod.DELETE.name()
      )
      .maxAge(3600);
  }
}
