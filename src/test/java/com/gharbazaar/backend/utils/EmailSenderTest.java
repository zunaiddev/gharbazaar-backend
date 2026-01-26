package com.gharbazaar.backend.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("mail")
class EmailSenderTest {
    @Autowired
    private EmailSender sender;

    @BeforeAll
    static void init() {
        DotEnv.load();
    }

    @Test
    void sendVerificationEmail() {
        sender.sendVerificationEmail("John", "work87t@gmail.com", "dummy token");
    }

    @Test
    void sendForgotPasswordEmail() {
        sender.sendForgotPasswordEmail("John", "work87t@gmail.com", "dummy token");
    }
}