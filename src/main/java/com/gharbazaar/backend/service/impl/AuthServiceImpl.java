package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.LoginReq;
import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
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

        String token = jwtGenerator.verification(user.getId(), user.getEmail());

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

        String token = jwtGenerator.verification(user.getId(), user.getEmail());

        return new LoginRes(token, user.getStatus());
    }
}
