package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.LoginReq;
import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
import com.gharbazaar.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public SignupRes signup(@RequestBody @Valid SignupReq req) {
        return authService.signup(req);
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody @Valid LoginReq req) {
        return authService.login(req);
    }
}
