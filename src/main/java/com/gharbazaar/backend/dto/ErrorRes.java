package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public record ErrorRes(int status, String code, String message) {
    public ErrorRes(HttpStatus status, ErrorCode code, String message) {
        this(status.value(), code.toString(), message);
    }

    public ErrorRes(HttpStatus status) {
        this(status.value(), status.name(), status.getReasonPhrase());
    }

    public ErrorRes(HttpStatus status, String message) {
        this(status.value(), status.name(), message);
    }
}