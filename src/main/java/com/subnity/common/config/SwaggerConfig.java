package com.subnity.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
      .title("구독 비용 관리 서비스 API 명세서")
      .description("Subnity API")
      .version("mvp");

    // JWT 구현 시 제거
//    String jwtSchemeName = "JWT TOKEN";
//    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
//
//    Components components = new Components()
//      .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
//        .name("Authorization")
//        .type(SecurityScheme.Type.HTTP)
//        .scheme("bearer")
//        .bearerFormat("JWT")
//        .in(SecurityScheme.In.HEADER));

    return new OpenAPI()
      .info(info)
      .addServersItem(new Server().url("http://localhost:8080"));
//      .addSecurityItem(securityRequirement)
//      .components(components);
  }
}
