package com.gharbazaar.backend.exception;

import org.springframework.security.authentication.AccountStatusException;

public class InactiveUserException extends AccountStatusException {
    public InactiveUserException(String message) {
        super(message);
    }
}