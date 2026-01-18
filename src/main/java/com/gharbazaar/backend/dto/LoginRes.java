package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.UserStatus;

import java.time.LocalDateTime;

public record LoginRes(String token, UserStatus status, LocalDateTime deleteAt) {
    public LoginRes(String token, UserStatus status) {
        this(token, status, null);
    }
}