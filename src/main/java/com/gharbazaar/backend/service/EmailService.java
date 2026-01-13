package com.gharbazaar.backend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);

    void sendEmailAsync(String to, String subject, String content);
}