package com.subnity.common.config;

import com.subnity.auth.service.OAuth2Service;
import com.subnity.auth.filter.AuthFilter;
import com.subnity.auth.handler.AuthFailureHandler;
import com.subnity.auth.handler.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig : Spring Security 설정 파일
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2Service oAuth2Service;
  private final AuthSuccessHandler successHandler;
  private final AuthFailureHandler failureHandler;

  /**
   * AuthFilter를 Bean으로 등록하는 메서드
   * @return : AuthFilter 객체 반환
   */
  @Bean
  public AuthFilter authFilter() {
    return new AuthFilter();
  }

  /**
   * SecurityFilterChain 설정 메서드
   * @param http : HttpSecurity 객체
   * @return : HttpSecurity 객체 반환
   * @throws Exception : 예외 처리
   */
  @Bean
  public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

    http.authorizeHttpRequests(authorizeRequests -> {
      authorizeRequests.requestMatchers( // Security 인증 filter 패스
        "/health", "/login/**", "/enum/**"
      ).permitAll()
      .requestMatchers( // Resource 관련 Url 처리
        "/favicon.ico",
        "/css/**",
        "/js/**",
        "/images/**"
      )
      .permitAll()
      .requestMatchers( // Swagger 관련 Url 처리
        "/v1/api-docs",
        "/v1/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
      ).permitAll()
      .anyRequest().authenticated();
    });

    // OAuth2 인증 설정
    http.oauth2Login(oauth2Config -> {
      oauth2Config.loginPage("/login")
        .userInfoEndpoint(e -> e.userService(oAuth2Service))
        .successHandler(successHandler)
        .failureHandler(failureHandler);
    });

    http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
