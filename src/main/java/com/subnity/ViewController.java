package com.subnity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

  @GetMapping(value = "/login")
  public String login() {
    return "login";
  }
}
