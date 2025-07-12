package com.subnity.common.config;

import com.subnity.security.OAuth2Service;
import com.subnity.security.filter.AuthFilter;
import com.subnity.security.handler.AuthFailureHandler;
import com.subnity.security.handler.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2Service oAuth2Service;
  private final AuthSuccessHandler successHandler;
  private final AuthFailureHandler failureHandler;

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
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );

    http.authorizeHttpRequests(authorizeRequests -> {
      authorizeRequests.requestMatchers( // Security 인증 filter 패스
        "/health", "/test/**", "/login/**"
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
    http.oauth2Login(oauth2LoginConfig -> {
      oauth2LoginConfig.loginPage("/login")
        .userInfoEndpoint(e -> e.userService(oAuth2Service))
        .successHandler(successHandler)
        .failureHandler(failureHandler);
    });

    http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * AuthFilter를 Bean으로 등록하는 메서드
   * @return : AuthFilter 객체 반환
   */
  @Bean
  public AuthFilter authFilter() {
    return new AuthFilter();
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
