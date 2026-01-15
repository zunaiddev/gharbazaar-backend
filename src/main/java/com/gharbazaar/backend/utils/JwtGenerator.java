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

    public String verification(long id) {
        return jwtService.generateToken(id, getPayload(Purpose.VERIFICATION), Duration.ofHours(12L));
    }

    public String authentication(long id) {
        return jwtService.generateToken(id, getPayload(Purpose.AUTHENTICATION), Duration.ofMinutes(15L));
    }

    public String resetPassword(long id) {
        return jwtService.generateToken(id, getPayload(Purpose.RESET_PASSWORD), Duration.ofMinutes(15L));
    }

    public String refresh(long id, String email) {
        return jwtService.generateToken(id, getPayload(Purpose.REFRESH), Duration.ofDays(7L));
    }

    private Map<String, Object> getPayload(Purpose purpose) {
        return Map.of("purpose", purpose);
    }
}
