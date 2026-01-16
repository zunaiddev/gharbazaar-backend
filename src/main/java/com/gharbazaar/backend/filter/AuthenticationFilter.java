package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.dto.JwtPayload;
import com.gharbazaar.backend.enums.ErrorCode;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.InactiveUserException;
import com.gharbazaar.backend.exception.InvalidPurposeException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.utils.Helper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserService service;
    private final Helper helper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return !req.getRequestURI().startsWith("/api/users");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, @NonNull HttpServletResponse res,
                                    @NonNull FilterChain chain) throws IOException {
        final JwtPayload payload = (JwtPayload) req.getAttribute("payload");

        try {
            Purpose purpose = payload.purpose();

            if (!purpose.equals(Purpose.AUTHENTICATION)) {
                throw new InvalidPurposeException("Invalid Token Purpose");
            }

            User user = service.findById(Long.parseLong(payload.subject().toString()));

            if (!user.isEnabled()) {
                throw new DisabledException("User is disabled");
            }

            if (user.isLocked()) {
                throw new LockedException("User is locked");
            }

            if (!user.getStatus().equals(UserStatus.ACTIVE)) {
                throw new InactiveUserException(user.getStatus() + " User is not allowed");
            }

            UserDetails userDetails = new UserPrincipal(user);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            ));

            chain.doFilter(req, res);
        } catch (NumberFormatException e) {
            logger.warn("Invalid Subject: ", e);
            helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SUBJECT, "Invalid Token Subject");
        } catch (EntityNotFoundException e) {
            logger.warn("User not found: ", e);
            helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_FOUND, "User not found");
        } catch (InvalidPurposeException e) {
            logger.warn("Invalid Purpose: ", e);
            helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PURPOSE, e.getMessage());
        } catch (AccountStatusException e) {
            logger.warn("Account Status Exception: ", e);

            switch (e) {
                case DisabledException _ -> {
                    helper.sendErrorRes(res, HttpStatus.FORBIDDEN, ErrorCode.DISABLED, e.getMessage());
                    return;
                }
                case LockedException _ -> {
                    helper.sendErrorRes(res, HttpStatus.LOCKED, ErrorCode.LOCKED, e.getMessage());
                    return;
                }
                case InactiveUserException _ -> {
                    helper.sendErrorRes(res, HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, e.getMessage());
                    return;
                }
                default -> helper.sendErrorRes(res, HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, "Forbidden");
            }

            helper.sendErrorRes(res, HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, "Forbidden");
        } catch (Exception e) {
            logger.error("Error: ", e);
            helper.sendErrorRes(res, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}