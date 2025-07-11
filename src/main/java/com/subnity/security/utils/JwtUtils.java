package com.subnity.security.utils;

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

  public static String createAccessToken(String userId, String role) {
    claims.put("id", userId);
    claims.put("name", userId);
    claims.put("role", role);

    return Jwts.builder()
      .setHeaderParam("typ", "JWT")
      .setHeaderParam("alg", "HS256")
      .addClaims(claims)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + (1209600 * 1000)))
      .signWith(secretKey)
      .compact();
  }

  public static String createRefreshToken(String userId, String role) {
    claims.put("id", userId);
    claims.put("name", userId);
    claims.put("role", role);

    return Jwts.builder()
      .setHeaderParam("typ", "JWT")
      .setHeaderParam("alg", "HS256")
      .addClaims(claims)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + (2628000L * 1000)))
      .signWith(secretKey)
      .compact();
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
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("잘못된 JWT 토큰 -> ", e);
    } catch (ExpiredJwtException e) {
      log.error("만료된 JWT 토큰 -> ", e);
    } catch (UnsupportedJwtException e) {
      log.error("지원되지 않는 JWT 토큰 -> ", e);
    } catch (IllegalArgumentException e) {
      log.error("JWT 주장 문자열이 비어 있습니다 -> ", e);
    }

    return false;
  }
}
