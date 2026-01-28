package com.gharbazaar.backend.exception;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRes handleException(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes handleLockedException(BadCredentialsException ex) {
        log.error("Bad Credentials: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.UNAUTHORIZED, "Invalid Email or Password");
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ErrorRes handleLockedException(LockedException ex) {
        log.error("User is Locked: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.LOCKED, "User is Locked");
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorRes handleDisabledException(DisabledException ex) {
        log.error("User is Disabled: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.FORBIDDEN, ErrorCode.DISABLED, "User is Disabled");
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorRes handleConflictException(ConflictException ex) {
        log.error("Conflict Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRes handleResourceException(NoResourceFoundException ex) {
        log.error("Not Found Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.NOT_FOUND, "No static resource " + ex.getResourcePath());
    }

    @ExceptionHandler(InvalidPurposeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorRes handlePurposeException(InvalidPurposeException ex) {
        log.error("Invalid Token Purpose Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.FORBIDDEN, ErrorCode.INVALID_PURPOSE, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        log.error("Invalid Request body: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST_BODY, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Invalid Request body: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid Request body: ", ex);
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.MESSAGE_NOT_READEABLE, ex.getMessage());
    }

    @ExceptionHandler(OAuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleOAuthException(OAuthException ex) {
        log.error("OAuth Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.OAUTH_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ScopeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleScopeException(ScopeException ex) {
        log.error("Invalid OAuth Scope: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SCOPE, ex.getMessage());
    }

    @ExceptionHandler(UnverifiedEmail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRes handleUnverifiedEmailException(UnverifiedEmail ex) {
        log.error("Email is not verified: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.BAD_REQUEST, ErrorCode.UNVERIFIED_EMAIL, ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErrorRes handleMultipartException(MultipartException ex) {
        log.error("Multipart Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.UNPROCESSABLE_CONTENT, ErrorCode.INVALID_REQUEST_BODY, ex.getMessage());
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErrorRes handleMultipartException(InvalidPurposeException ex) {
        log.error("Invalid file type Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.UNPROCESSABLE_CONTENT, ErrorCode.INVALID_REQUEST_BODY, "Invalid file type. Only .jpeg, .jpg, .png are allowed.");
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErrorRes handleSizeLimitExceededException(SizeLimitExceededException ex) {
        log.error("Invalid file Size Exception: {}", ex.getMessage());
        return new ErrorRes(HttpStatus.UNPROCESSABLE_CONTENT, ErrorCode.INVALID_REQUEST_BODY, "Request Size Limit Exceeded.");
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
