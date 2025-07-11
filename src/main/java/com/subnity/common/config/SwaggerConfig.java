package com.subnity.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
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

//    SecurityRequirement securityRequirement = new SecurityRequirement().addList("oauth2");
//    Components components = new Components()
//      .addSecuritySchemes("oauth2",
//        new SecurityScheme()
//          .type(SecurityScheme.Type.OAUTH2)
//          .flows(new OAuthFlows()
//            .authorizationCode(new OAuthFlow()
//              .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
//              .tokenUrl("https://oauth2.googleapis.com/token")
//              .scopes(new Scopes()
//                .addString("profile", "Profile")
//                .addString("email", "Email")
//                .addString("https://www.googleapis.com/auth/gmail.readonly", "Gmail Readonly")
//              )
//            )
//          )
//      );

    SecurityRequirement securityRequirement = new SecurityRequirement().addList("Google OAuth2");
    Components components = new Components()
      .addSecuritySchemes("Google OAuth2",
        new SecurityScheme()
          .type(SecurityScheme.Type.OAUTH2)
          .flows(new OAuthFlows()
            .authorizationCode(new OAuthFlow()
              .authorizationUrl("/oauth2/authorization/google")
              .tokenUrl("https://oauth2.googleapis.com/token")
              .scopes(new Scopes()
                .addString("profile", "Profile")
                .addString("email",   "Email")
                .addString("https://www.googleapis.com/auth/gmail.readonly", "Gmail Read‑only")
              )
            )
          )
      );

    return new OpenAPI()
      .info(info)
      .addServersItem(new Server().url("/"))
      .addSecurityItem(securityRequirement)
      .components(components);
  }
}
