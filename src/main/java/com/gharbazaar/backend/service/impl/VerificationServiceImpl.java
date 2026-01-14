package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.service.VerificationService;
import com.gharbazaar.backend.utils.JwtGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    @Override
    public LoginRes verifyEmail(User user) {
        if (user.isEnabled() || !user.getStatus().equals(UserStatus.UNVERIFIED)) {
            throw new ConflictException("User already verified");
        }

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);
        user = userService.update(user);

        String token = jwtGenerator.authentication(user.getId(), user.getEmail());

        System.out.println("Authentication Token:\n" + token);

        return new LoginRes(token, user.getStatus());
    }
}