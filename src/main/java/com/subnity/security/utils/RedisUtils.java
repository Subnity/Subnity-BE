package com.subnity.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtils {
  private final RedisTemplate<String, Object> redisTemplate;

  public void save(String token) {
    String memberId = JwtUtils.getMemberId(token);
    redisTemplate.opsForValue().set("rf-" + memberId, token, Duration.ofSeconds(2628000));
  }

  public String get(String token) {
    String memberId = JwtUtils.getMemberId(token);
    return (String) redisTemplate.opsForValue().get("rf-" + memberId);
  }

  public void delete(String token) {
    String memberId = JwtUtils.getMemberId(token);
    redisTemplate.delete("rf-" + memberId);
  }
}
