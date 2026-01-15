package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.*;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.AuthService;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.utils.EmailSender;
import com.gharbazaar.backend.utils.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtGenerator jwtGenerator;
    private final EmailSender emailSender;

    @Override
    public SignupRes signup(SignupReq req) {
        User user = userService.create(req.name(), req.email(), encoder.encode(req.password()));

        String token = jwtGenerator.verification(user.getId());

        System.out.println("Verification Token:\n" + token);
        emailSender.sendVerificationEmail(user.getEmail(), token);
        return new SignupRes(user);
    }

    @Override
    public LoginRes login(LoginReq req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        if (!auth.isAuthenticated()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = ((UserPrincipal) Objects.requireNonNull(auth.getPrincipal())).user();

        String token = jwtGenerator.authentication(user.getId());

        return new LoginRes(token, user.getStatus());
    }

    @Override
    public ForgotPasswordRes forgotPassword(ForgotPasswordReq req) {
        final User user = userService.findByEmail(req.email());

        String token = jwtGenerator.resetPassword(user.getId());
        emailSender.sendForgotPasswordEmail(user.getEmail(), token);

        System.out.println("Reset Password Token:\n" + token);
        return new ForgotPasswordRes(user.getId(), user.getEmail());
    }
}
