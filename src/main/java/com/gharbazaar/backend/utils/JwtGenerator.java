package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final JwtService jwtService;

    public String verification(long id, String email) {
        return jwtService.generateToken(id, getPayload(Purpose.VERIFICATION, email), Duration.ofHours(12L));
    }

    public String authentication(long id, String email) {
        return jwtService.generateToken(id, getPayload(Purpose.AUTHENTICATION, email), Duration.ofMinutes(15L));
    }

    public String refresh(long id, String email) {
        return jwtService.generateToken(id, Map.of("purpose", Purpose.REFRESH), Duration.ofDays(7L));
    }

    private Map<String, Object> getPayload(Purpose purpose, String email) {
        return Map.of("purpose", purpose, "email", email);
    }
}
