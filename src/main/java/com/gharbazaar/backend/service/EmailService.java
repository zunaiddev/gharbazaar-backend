package com.gharbazaar.backend.service;

import com.gharbazaar.backend.enums.EmailAlia;

public interface EmailService {
    void sendEmail(EmailAlia from, String to, String subject, String content);

    void sendEmailAsync(EmailAlia from, String to, String subject, String content);
}