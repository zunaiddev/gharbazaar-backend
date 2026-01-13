package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.dto.JwtPayload;
import com.gharbazaar.backend.enums.ErrorCode;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.service.JwtService;
import com.gharbazaar.backend.utils.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Helper helper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String URI = request.getRequestURI();
        return URI.startsWith("/api/auth") || URI.startsWith("/api/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, ErrorCode.MISSING_TOKEN, "Authorization header is null or Invalid");
            return;
        }

        String token = header.substring(7);

        try {
            Claims claims = jwtService.extractClaims(token);
            JwtPayload payload = new JwtPayload(claims.getSubject(), claims.get("email", String.class),
                    Purpose.valueOf(claims.get("purpose", String.class)), claims);

            req.setAttribute("payload", payload);
        } catch (ExpiredJwtException exp) {
            log.warn("Expired Jwt Token: {}", exp.getMessage());
            helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_JWT, "Token has expired");
            return;
        } catch (JwtException exp) {
            log.warn("Invalid Jwt Token: {}", exp.getMessage());
            helper.sendErrorRes(res, HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_JWT, "Invalid Jwt Token");
            return;
        } catch (Exception exp) {
            log.error("Error: {}", exp.getMessage());
            helper.sendErrorRes(res, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "Something went wrong");
            return;
        }

        chain.doFilter(req, res);
    }
}