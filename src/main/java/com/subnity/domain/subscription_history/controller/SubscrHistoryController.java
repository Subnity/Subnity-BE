package com.subnity.domain.subscription_history.controller;

import com.subnity.domain.subscription_history.service.SubscrHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subscr-history")
@RequiredArgsConstructor
@Tag(name = "Subscription History", description = "구독 히스토리 관련 API")
public class SubscrHistoryController {
  private final SubscrHistoryService subscrHistoryService;


}
