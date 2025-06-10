package com.OrderNet.ProyWebIntegrado.service.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

  @Autowired
  private Environment env;

  private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 24;
  private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT_SECRET"));
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails, ACCESS_TOKEN_EXPIRATION);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return generateToken(userDetails, REFRESH_TOKEN_EXPIRATION);
  }

  private String generateToken(UserDetails userDetails, long expirationMillis) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + expirationMillis);

    return Jwts.builder()
        .header().type("JWT").and()
        .claims()
        .subject(userDetails.getUsername())
        .issuedAt(now)
        .expiration(expiration)
        .and()
        .signWith(getSigningKey(), SIG.HS256)
        .compact();
  }

  public String getToken(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser()
          .verifyWith(getSigningKey())
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }
}
