package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.dto.JwtPayload;
import com.gharbazaar.backend.enums.ErrorCode;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.exception.InvalidPurposeException;
import com.gharbazaar.backend.service.JwtService;
import com.gharbazaar.backend.utils.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String URI = request.getRequestURI();
        return URI.startsWith("/api/health") || URI.startsWith("/api/auth") ||
                URI.startsWith("/api/swagger-ui") || URI.startsWith("/api/docs")
                || URI.startsWith("/api/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull FilterChain chain) throws IOException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("Invalid Authorization Header: {}", header);
            Helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, ErrorCode.MISSING_TOKEN, "Authorization header is null or Invalid");
            return;
        }

        String token = header.substring(7);

        try {
            Claims claims = jwtService.extractClaims(token);

            String subject = claims.getSubject();

            if (subject == null) {
                throw new JwtException("Invalid Jwt Token: Subject is null");
            }

            JwtPayload payload = new JwtPayload(subject, claims.get("email", String.class),
                    Purpose.fromString(claims.get("purpose", String.class)), token, claims);

            req.setAttribute("payload", payload);

            chain.doFilter(req, res);
        } catch (JwtException exp) {
            log.warn("Jwt Exception: {}", exp.getMessage());
            switch (exp) {
                case MalformedJwtException _ ->
                        Helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.MALFORMED_JWT, "Malformed Jwt Token");
                case ExpiredJwtException _ ->
                        Helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_JWT, "Token has expired");
                default ->
                        Helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_JWT, "Invalid Jwt Token");
            }
        } catch (InvalidPurposeException exp) {
            log.warn("Invalid Purpose: {}", exp.getMessage());
            Helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_PURPOSE, exp.getMessage());
        } catch (Exception exp) {
            log.error("Error: {}", exp.getMessage());
            Helper.sendErrorRes(res, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}