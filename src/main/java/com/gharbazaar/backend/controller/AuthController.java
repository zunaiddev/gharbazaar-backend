package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.*;
import com.gharbazaar.backend.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<LoginRes> googleOAuth(@PathParam("code") @NotBlank(message = "Code is null or blank") String code, HttpServletResponse res) {
        return authService.googleOAuth(code);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupRes signup(@RequestBody @Valid SignupReq req) {
        return authService.signup(req);
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody @Valid LoginReq req) {
        return authService.login(req);
    }

    @PostMapping("/forgot-password")
    public ForgotPasswordRes forgotPassword(@RequestBody @Valid ForgotPasswordReq req) {
        return authService.forgotPassword(req);
    }
}
