package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public record ErrorRes(HttpStatus status, ErrorCode code, String message) {
}
