package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.dto.JwtPayload;
import com.gharbazaar.backend.enums.ErrorCode;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.exception.InvalidPurposeException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.utils.Helper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VerificationFilter extends OncePerRequestFilter {
    private final Helper helper;
    private final UserService service;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().toLowerCase().startsWith("/api/verify");
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest req, @NonNull HttpServletResponse res,
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        JwtPayload payload = (JwtPayload) req.getAttribute("payload");

        try {
            Purpose purpose = payload.purpose();

            if (!Set.of(Purpose.VERIFICATION, Purpose.RESET_PASSWORD).contains(purpose)) {
                throw new InvalidPurposeException("Invalid Token Purpose");
            }

            User user = service.findById(Long.parseLong(payload.subject().toString()));

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
            helper.sendErrorRes(res, HttpStatus.BAD_REQUEST, e.getCode(), e.getMessage());
        }

    }
}
