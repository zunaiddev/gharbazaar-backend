package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.*;
import com.gharbazaar.backend.enums.OAuthClient;
import com.gharbazaar.backend.enums.Role;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.oauth.GoogleAuth;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.AuthService;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.utils.EmailSender;
import com.gharbazaar.backend.utils.Helper;
import com.gharbazaar.backend.utils.JwtGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
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
    private final GoogleAuth googleAuth;
    private final JwtGenerator jwtGenerator;
    private final EmailSender emailSender;

    @Override
    public ResponseEntity<LoginRes> googleOAuth(String code, HttpServletResponse res) {
        OAuthUser oAuthUser = googleAuth.getOAuthUser(code);
        User user = userService.findByEmail(oAuthUser.email(), false);

        if (user == null || !user.isEnabled()) {
            final User persisted;

            if (user == null) {
                persisted = userService.save(User.builder().name(oAuthUser.name()).email(oAuthUser.email())
                        .password(null).role(Role.USER).status(UserStatus.ACTIVE).oAuthClient(OAuthClient.GOOGLE)
                        .enabled(true).locked(false).build());
            } else {
                user.setName(oAuthUser.name());
                user.setOAuthClient(OAuthClient.GOOGLE);
                user.setEnabled(true);
                user.setStatus(UserStatus.ACTIVE);
                user.setPassword(null);

                persisted = userService.update(user);
            }


            Helper.setRefreshCookie(res, jwtGenerator.refresh(persisted.getId(), persisted.getRole()));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new LoginRes(jwtGenerator.authentication(persisted.getId(), persisted.getRole()), persisted.getStatus()));
        }

        if (user.isLocked()) throw new LockedException("User is locked");

        return ResponseEntity.ok(this.loginUser(user, res));
    }

    @Override
    public SignupRes signup(SignupReq req) {
        User user = userService.create(req.name(), req.email(), encoder.encode(req.password()));

        String token = jwtGenerator.verification(user.getId(), user.getRole());

        System.out.println("Verification Token:\n" + token);
        emailSender.sendVerificationEmail(user.getEmail(), token);
        return new SignupRes(user);
    }

    @Override
    public LoginRes login(LoginReq req, HttpServletResponse res) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        if (!auth.isAuthenticated()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = ((UserPrincipal) Objects.requireNonNull(auth.getPrincipal())).user();

        return this.loginUser(user, res);
    }

    @Override
    public void logout(HttpServletResponse res) {
        Helper.removeCookie(res);
    }

    @Override
    public ForgotPasswordRes forgotPassword(ForgotPasswordReq req) {
        final User user = userService.findByEmail(req.email());

        String token = jwtGenerator.resetPassword(user.getId(), user.getRole());
        emailSender.sendForgotPasswordEmail(user.getEmail(), token);

        System.out.println("Reset Password Token:\n" + token);
        return new ForgotPasswordRes(user.getId(), user.getEmail());
    }

    private LoginRes loginUser(User user, HttpServletResponse res) {
        if (user.getStatus().equals(UserStatus.PENDING_DELETE)) {
            return new LoginRes(jwtGenerator.reactivate(user.getId(),
                    user.getRole()), user.getStatus(), user.getDeleteAt());
        }

        Helper.setRefreshCookie(res, jwtGenerator.refresh(user.getId(), user.getRole()));
        return new LoginRes(jwtGenerator.authentication(user.getId(), user.getRole()), user.getStatus());
    }
}
