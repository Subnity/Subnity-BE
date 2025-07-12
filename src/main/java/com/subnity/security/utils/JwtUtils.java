package com.subnity.security.utils;

import com.subnity.security.dto.JwtBuilder;
import com.subnity.security.dto.JwtClaimsDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

  @Value("${jwt.secret}")
  private String jwtSecretKey;

  private static Key secretKey;
  private static Claims claims;

  @PostConstruct
  public void init() {
    secretKey = Keys.hmacShaKeyFor(this.jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    claims = Jwts.claims();
  }

  public static JwtBuilder createToken(JwtClaimsDto dto) {
    claims.put("id", dto.getMemberId());
    claims.put("role", dto.getRole().name());

    Date now = new Date(System.currentTimeMillis());
    Date accessExpirationDate = new Date(System.currentTimeMillis() + (60 * 1000));
    Date refreshExpirationDate = new Date(System.currentTimeMillis() + (120 * 1000));

    String accessToken = Jwts.builder()
      .setHeaderParam("typ", "JWT")
      .setHeaderParam("alg", "HS256")
      .addClaims(claims)
      .setIssuedAt(now)
      .setExpiration(accessExpirationDate)
      .signWith(secretKey)
      .compact();

    String refreshToken = Jwts.builder()
      .setHeaderParam("typ", "JWT")
      .setHeaderParam("alg", "HS256")
      .addClaims(claims)
      .setIssuedAt(now)
      .setExpiration(refreshExpirationDate)
      .signWith(secretKey)
      .compact();

    return JwtBuilder.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();
  }

  // 유저 아이디 가져오기
  public static String getMemberId(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("id", String.class);
    } catch (ExpiredJwtException e) {
      return e.getClaims().get("id", String.class);
    }
  }

  // 유저 이름 가져오기
  public static String getMemberName(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("name", String.class);
    } catch (ExpiredJwtException e) {
      return e.getClaims().get("name", String.class);
    }
  }

  // 권한 가져오기
  public static String getRole(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
    } catch (ExpiredJwtException e) {
      return e.getClaims().get("role", String.class);
    }
  }

  // 만료 시간 확인
  public static boolean getValidateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("잘못된 JWT 토큰 > {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("만료된 JWT 토큰 > {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("지원되지 않는 JWT 토큰 > {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT 주장 문자열이 비어 있습니다 > {}", e.getMessage());
    }

    return false;
  }
}
