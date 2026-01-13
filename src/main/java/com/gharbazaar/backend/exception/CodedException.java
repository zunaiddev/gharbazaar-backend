package com.gharbazaar.backend.exception;

import com.gharbazaar.backend.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CodedException extends RuntimeException {
    protected final ErrorCode code;

    public CodedException(ErrorCode code, String message) {
        this.code = code;
        super(message);
    }

    public CodedException(String message) {
        this(ErrorCode.NONE, message);
    }
}
