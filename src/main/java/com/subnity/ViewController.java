package com.subnity;

import com.subnity.common.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
//@RestController
//@Tag(name = "Login")
public class ViewController {

//  @GetMapping(value = "/login")
//  public ApiResponse<Void> login() {
//    return ApiResponse.onSuccess();
//  }

  @GetMapping(value = "/login")
  public String login() {
    return "login";
  }
}
