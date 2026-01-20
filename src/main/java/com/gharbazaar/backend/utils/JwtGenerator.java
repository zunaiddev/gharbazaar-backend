package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.enums.Role;
import com.gharbazaar.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final JwtService jwtService;

    public String verification(long id, Role role) {
        return jwtService.generateToken(id, getPayload(Purpose.VERIFICATION, role), Duration.ofHours(12L));
    }

    public String authentication(long id, Role role) {
        return jwtService.generateToken(id, getPayload(Purpose.AUTHENTICATION, role), Duration.ofMinutes(15L));
    }

    public String resetPassword(long id, Role role) {
        return jwtService.generateToken(id, getPayload(Purpose.RESET_PASSWORD, role), Duration.ofMinutes(15L));
    }

    public String refresh(long id, Role role) {
        return jwtService.generateToken(id, getPayload(Purpose.REFRESH, role), Duration.ofDays(15L));
    }

    public String reactivate(long id, Role role) {
        return jwtService.generateToken(id, getPayload(Purpose.REACTIVATE, role), Duration.ofMinutes(15L));
    }

    private Map<String, Object> getPayload(Purpose purpose, Role role) {
        return Map.of("purpose", purpose, "role", role);
    }


}
