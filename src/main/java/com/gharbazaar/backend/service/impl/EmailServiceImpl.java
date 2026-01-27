package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.enums.EmailAlia;
import com.gharbazaar.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;

    @Override
    public void sendEmail(EmailAlia alia, String to, String subject, String content) {
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress(alia.getAlias(), "Gharbazaar"));
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setTo(to);
            helper.setSentDate(new Date(System.currentTimeMillis()));
        } catch (MessagingException | UnsupportedEncodingException exp) {
            throw new RuntimeException(exp);
        }

        sender.send(message);

        System.out.println("Email Sent to: " + to);
    }


    @Override
    public void sendEmailAsync(EmailAlia alia, String to, String subject, String content) {
        sendEmail(alia, to, subject, content);
    }
}