package com.gharbazaar.backend.service;

import com.gharbazaar.backend.service.impl.JwtServiceImpl;
import com.gharbazaar.backend.utils.JwtGenerator;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.*;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtServiceTest {
    static String token;
    private final JwtService jwtService = new JwtServiceImpl("test-jwt-secret-key-test-secret-key-test-key");

    private final JwtGenerator jwtGenerator = new JwtGenerator(jwtService);

    @Test
    @Order(1)
    void generateToken() throws InterruptedException {
        token = jwtService.generateToken("john@gmail.com", null, Duration.ofMinutes(1L));
        token = jwtGenerator.authentication(1L);

        Assertions.assertNotNull(token);

        System.out.println(token);
    }

    @Test
    @Order(2)
    void extractClaims() {
        Claims claims = jwtService.extractClaims(token);

        Assertions.assertNotNull(claims);

        System.out.println(claims.entrySet());
        System.out.println(claims.getId());
    }

    @Test
    void getSubject() {
        Object subject = jwtService.getSubject(token);
        System.out.println(subject);
    }
}