package com.gharbazaar.backend.enums;

import com.gharbazaar.backend.exception.InvalidPurposeException;

public enum Purpose {
    VERIFICATION, REFRESH, AUTHENTICATION, RESET_PASSWORD, REACTIVATE;

    public static Purpose fromString(String purpose) {
        try {
            return Purpose.valueOf(purpose);
        } catch (IllegalArgumentException exp) {
            throw new InvalidPurposeException("Invalid Token Purpose: " + purpose);
        }
    }
}
