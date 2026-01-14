package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService service;

    @PutMapping("/email")
    public LoginRes verifyEmail(@AuthenticationPrincipal UserPrincipal details) {
        return service.verifyEmail(details.user());
    }
}
