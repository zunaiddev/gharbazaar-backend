package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.ResetPasswordReq;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.exception.InvalidPurposeException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.service.VerificationService;
import com.gharbazaar.backend.utils.JwtGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtGenerator jwtGenerator;

    @Override
    public LoginRes verifyEmail(User user, Purpose purpose) {
        if (!purpose.equals(Purpose.VERIFICATION)) {
            throw new InvalidPurposeException("Invalid Token Purpose");
        }

        if (user.isEnabled() || !user.getStatus().equals(UserStatus.UNVERIFIED)) {
            throw new ConflictException("User already verified");
        }

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);
        user = userService.update(user);

        String token = jwtGenerator.authentication(user.getId());

        System.out.println("Authentication Token:\n" + token);

        return new LoginRes(token, user.getStatus());
    }

    @Override
    public void resetPassword(User user, ResetPasswordReq req, Purpose purpose) {
        if (!purpose.equals(Purpose.RESET_PASSWORD)) {
            throw new InvalidPurposeException("Invalid Token Purpose");
        }

        final String password = req.password();

        if (encoder.matches(req.password(), user.getPassword())) {
            throw new ConflictException("New password cannot be the same as the old one");
        }

        user.setPassword(encoder.encode(password));
        userService.update(user);
    }
}