package com.gharbazaar.backend.exception;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRes handleException(Exception ex) {
        log.error("Internal Server Error: ", ex);
        return new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "Something Went Wrong");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes handleLockedException(BadCredentialsException ex) {
        log.error("User is Locked: ", ex);
        return new ErrorRes(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, "Invalid Email or Password");
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ErrorRes handleLockedException(LockedException ex) {
        log.error("User is Locked: ", ex);
        return new ErrorRes(HttpStatus.LOCKED, ErrorCode.LOCKED, "User is Locked");
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorRes handleDisabledException(DisabledException ex) {
        log.error("User is Disabled: ", ex);
        return new ErrorRes(HttpStatus.FORBIDDEN, ErrorCode.DISABLED, "User is Disabled");
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorRes handleConflictException(ConflictException ex) {
        log.error("Conflict Exception: ", ex);
        return new ErrorRes(HttpStatus.CONFLICT, ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();

        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }


        log.warn("Validation Error: {}", errors);

        return errors;
    }


}
