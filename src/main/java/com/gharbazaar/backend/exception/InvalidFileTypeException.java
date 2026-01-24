package com.gharbazaar.backend.exception;

import org.springframework.web.multipart.MultipartException;

public class InvalidFileTypeException extends MultipartException {
    public InvalidFileTypeException(String msg) {
        super(msg);
    }
}