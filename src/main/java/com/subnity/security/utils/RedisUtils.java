package com.subnity.security.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtils {

  private final RedisTemplate<String, Object> nonRedisTemplate;
  private static RedisTemplate<String, Object> redisTemplate;

  @PostConstruct
  public void init() {
    redisTemplate = this.nonRedisTemplate;
  }

  public static void save(String token) {
    String memberId = JwtUtils.getMemberId(token);
    redisTemplate.opsForValue().set("rf-" + memberId, token, Duration.ofSeconds(120));
  }

  public static String get(String token) {
    String memberId = JwtUtils.getMemberId(token);
    return (String) redisTemplate.opsForValue().get("rf-" + memberId);
  }

  public static void delete(String token) {
    String memberId = JwtUtils.getMemberId(token);
    redisTemplate.delete("rf-" + memberId);
  }
}
