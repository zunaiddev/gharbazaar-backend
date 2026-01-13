package com.gharbazaar.backend.exception;

import com.gharbazaar.backend.enums.ErrorCode;

public class ConflictException extends CodedException {
    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}