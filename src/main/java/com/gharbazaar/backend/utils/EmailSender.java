package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.enums.EmailAlia;
import com.gharbazaar.backend.model.Form;
import com.gharbazaar.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class EmailSender {
    private final EmailService emailService;
    private final String BASE_URL;
    private final String VERIFY_EMAIL_TEMPLATE;
    private final String RESET_PASSWORD_TEMPLATE;

    public EmailSender(EmailService emailService, @Value("${BASE_URL}") String baseUrl) {
        this.emailService = emailService;
        this.BASE_URL = baseUrl;

        try {
            FileReader verifyEmail = new FileReader("src/main/resources/templates/verify-email.html");
            FileReader resetPassword = new FileReader("src/main/resources/templates/reset-password.html");

            this.VERIFY_EMAIL_TEMPLATE = verifyEmail.readAllAsString();
            this.RESET_PASSWORD_TEMPLATE = resetPassword.readAllAsString();

            verifyEmail.close();
            resetPassword.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String name, String to, String token) {
        emailService.sendEmail(EmailAlia.NO_REPLY, to, "Verify your email", VERIFY_EMAIL_TEMPLATE
                .replace("${username}", name)
                .replace("${verificationLink}", getLink(token))
                .replace("${expiry}", "12 Hours")
        );
    }

    public void sendForgotPasswordEmail(String name, String to, String token) {
        emailService.sendEmail(EmailAlia.NO_REPLY, to, "Reset your password", RESET_PASSWORD_TEMPLATE
                .replace("${username}", name)
                .replace("${verificationLink}", getLink(token))
                .replace("${expiry}", "15 Minutes")
                .replace("${email}", to));
    }

    public void sendFormEmail(Form form) {

    }

    public void sendAdminFormEmail(Form form, String to) {

    }

    private String getLink(String token) {
        return BASE_URL + token;
    }
}