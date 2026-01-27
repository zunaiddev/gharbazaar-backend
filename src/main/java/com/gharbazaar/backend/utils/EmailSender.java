package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.enums.EmailAlia;
import com.gharbazaar.backend.model.Form;
import com.gharbazaar.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EmailSender {
    private final EmailService emailService;
    private final String BASE_URL;
    private final String VERIFY_EMAIL_TEMPLATE;
    private final String RESET_PASSWORD_TEMPLATE;
    private final String FORM_EMAIL_TEMPLATE;
    private final String ADMIN_FORM_EMAIL_TEMPLATE;

    public EmailSender(EmailService emailService,
                       @Value("${BASE_URL}") String baseUrl) {

        this.emailService = emailService;
        this.BASE_URL = baseUrl;

        try {
            this.VERIFY_EMAIL_TEMPLATE = loadTemplate("templates/verify-email.html");
            this.RESET_PASSWORD_TEMPLATE = loadTemplate("templates/reset-password.html");
            this.FORM_EMAIL_TEMPLATE = loadTemplate("templates/form-email.html");
            this.ADMIN_FORM_EMAIL_TEMPLATE = loadTemplate("templates/admin-form-email.html");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load email templates", e);
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

    @Async
    public void sendFormEmail(Form form) {
        emailService.sendEmailAsync(EmailAlia.NO_REPLY, form.getEmail(),
                "Thank You for Contacting GharBazaar", this.FORM_EMAIL_TEMPLATE
                        .replace("${name}", form.getName())
                        .replace("${email}", form.getEmail())
        );
    }

    @Async
    public void sendAdminFormEmail(Form form, String to) {
        emailService.sendEmailAsync(EmailAlia.NO_REPLY, to, "New Form Submission",
                this.ADMIN_FORM_EMAIL_TEMPLATE
                        .replace("${name}", form.getName())
                        .replace("${email}", form.getEmail())
                        .replace("${phone}", form.getPhone())
                        .replace("${subject}", form.getSubject())
                        .replace("${message}", form.getMessage())
                        .replace("${referenceId}", form.getReferenceId())
                        .replace("${submittedAt}", form.getCreatedAt().toString())
        );
    }

    private String getLink(String token) {
        return BASE_URL + token;
    }

    private String loadTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}