package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {
    private final EmailService emailService;

    public void sendVerificationEmail(String to, String token) {
        emailService.sendEmail(to, "Verify your email", "Please verify your email by clicking this link: http://localhost:8080/auth/verification?token=" + token);
    }
}