package com.gharbazaar.backend.exception;

public class UnverifiedEmail extends OAuthException {
    public UnverifiedEmail(String message) {
        super(message);
    }
}