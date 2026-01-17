package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.ResetPasswordReq;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.VerificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class VerificationController {
    private final VerificationService service;

    @PutMapping("/email")
    public LoginRes verifyEmail(@AuthenticationPrincipal UserPrincipal details, @RequestAttribute Purpose purpose) {
        return service.verifyEmail(details.user(), purpose);
    }

    @PutMapping("/reset-password")
    public void resetPassword(@AuthenticationPrincipal UserPrincipal details,
                              @Valid @RequestBody ResetPasswordReq req, @RequestAttribute Purpose purpose) {
        service.resetPassword(details.user(), req, purpose);
    }
}
