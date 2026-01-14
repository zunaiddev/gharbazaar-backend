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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        return !req.getRequestURI().startsWith("/api/users");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        final JwtPayload payload = (JwtPayload) req.getAttribute("payload");

        try {
            Purpose purpose = payload.purpose();

            if (!purpose.equals(Purpose.AUTHENTICATION)) {
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
