package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.Purpose;
import io.jsonwebtoken.Claims;

public record JwtPayload(Object subject, String email, Purpose purpose, Claims claims) {
    public JwtPayload(Object subject, String email, Purpose purpose) {
        this(subject, email, purpose, null);
    }
}