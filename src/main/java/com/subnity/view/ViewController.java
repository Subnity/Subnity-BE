package com.subnity.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ViewController : 개발 테스트 용도로 사용할 Controller
 */
@Controller
public class ViewController {

  /**
   * OAuth2 로그인 테스트를 위한 로그인 페이지
   * @return : 로그인 페이지 반환
   */
  @GetMapping(value = "/login")
  public String login() {
    return "login";
  }

  /**
   * 개인정보처리방침 페이지
   * @return : 개인정보처리방침 페이지 반환
   */
  @GetMapping(value = "/privacy-policy")
  public String privacyPolicy() {
    return "privacy-policy";
  }
}
