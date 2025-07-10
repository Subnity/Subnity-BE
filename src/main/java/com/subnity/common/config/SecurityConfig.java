package com.subnity.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  /**
   * SecurityFilterChain 설정
   * @param http : HttpSecurity 객체
   * @return : HttpSecurity 객체 반환
   * @throws Exception : 예외 처리
   */
  @Bean
  public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .cors(AbstractHttpConfigurer::disable)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

    http.authorizeHttpRequests(authorizeRequests -> {
      authorizeRequests.requestMatchers( // Security 인증 filter 패스
        "/health", "/test/**", "/enum/**"
      ).permitAll()
      .requestMatchers( // Swagger 관련 Url 처리
        "/v1/api-docs",
        "/v1/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
      ).permitAll()
      .anyRequest().authenticated();
    });

    // OAuth2 클래스 구성 완료 후 주석 제거
//    http.oauth2Login(oauth2LoginConfig -> {
//      oauth2LoginConfig.loginPage("/login");
////        .authorizedClientService()
////        .successHandler()
////        .failureHandler();
//    });

    return http.build();
  }

  /**
   * 비밀번호 BCrypt 암호화 Bean
   * @return : BCrypt 암호화 인코더 반환
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
