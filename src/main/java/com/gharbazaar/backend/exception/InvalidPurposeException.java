package com.gharbazaar.backend.exception;

import com.gharbazaar.backend.enums.ErrorCode;

public class InvalidPurposeException extends CodedException {
    public InvalidPurposeException(String message) {
        super(ErrorCode.INVALID_PURPOSE, message);
    }
}
