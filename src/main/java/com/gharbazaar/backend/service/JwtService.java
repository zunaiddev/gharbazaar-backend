package com.gharbazaar.backend.service;

import io.jsonwebtoken.Claims;

import java.time.Duration;
import java.util.Map;

public interface JwtService {
    <T> String generateToken(T subject, Map<String, Object> claims, Duration expiry);

    Claims extractClaims(String token);

    Object getSubject(String token);
}