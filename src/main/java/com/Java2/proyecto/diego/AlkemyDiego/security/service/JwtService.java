package com.Java2.proyecto.diego.AlkemyDiego.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expiration}")
  private long jwtExpirationMs;

  private Key cachedSigningKey;

  public String generateToken(String username, List<String> roles) {
    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    if (cachedSigningKey == null) {
      String secret = jwtSecret.replace("\"", "");
      byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
      cachedSigningKey = Keys.hmacShaKeyFor(keyBytes);
    }
    return cachedSigningKey;
  }

}
