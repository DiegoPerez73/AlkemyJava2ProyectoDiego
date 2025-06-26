package com.Java2.proyecto.diego.AlkemyDiego.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private String jwtSecret;

  @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        jwtSecret = Encoders.BASE64.encode(key.getEncoded());
    long jwtExpirationMs = 3600000; // 1 hora

    setField(jwtService, "jwtSecret", jwtSecret);
        setField(jwtService, "jwtExpirationMs", jwtExpirationMs);
    }

    @Test
    void testGenerateToken_containsUsernameAndRolesAndExpiration() {
        String username = "testuser";
        List<String> roles = List.of("ADMIN", "USER");

        String token = jwtService.generateToken(username, roles);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(jwtSecret)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
        assertEquals(roles, claims.get("roles", List.class));
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    // Utilidad para setear campos privados vía reflexión
    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}