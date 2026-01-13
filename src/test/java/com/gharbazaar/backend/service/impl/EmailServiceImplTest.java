package com.gharbazaar.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceImplTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    void sendEmail() {
        emailService.sendEmail("john@gmail.com", "Test", "Test");
    }
}