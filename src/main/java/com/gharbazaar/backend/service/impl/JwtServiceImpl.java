package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${JWT_SECRET}") String jwtSecret) {
        secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().withoutPadding().encode(jwtSecret.getBytes()));
    }

    @Override
    public <T> String generateToken(T subject, Map<String, Object> claims, Duration expiry) {
        return Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .subject(subject.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expiry.toMillis() + System.currentTimeMillis()))
                .compact();
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Object getSubject(String token) {
        return this.extractClaims(token).getSubject();
    }
}