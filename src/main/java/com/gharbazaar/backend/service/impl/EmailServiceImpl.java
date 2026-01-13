package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

//            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setTo(to);
            helper.setSentDate(new Date(System.currentTimeMillis()));
        } catch (MessagingException exp) {
            throw new RuntimeException(exp);
        }

        sender.send(message);
    }

    @Async
    @Override
    public void sendEmailAsync(String to, String subject, String content) {
        sendEmail(to, subject, content);
    }
}
