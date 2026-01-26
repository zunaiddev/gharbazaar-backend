package com.gharbazaar.backend.service;

import com.gharbazaar.backend.enums.EmailAlia;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("mail")
class EmailServiceTest {
    @Autowired
    private EmailService service;

    @BeforeAll
    static void init() {
        DotEnv.load();
    }

    @Test
    void sendEmail() {
        service.sendEmail(EmailAlia.NO_REPLY, "work87t@gmail.com", "From noreply", "This is is from no reply");
        service.sendEmail(EmailAlia.SUPPORT, "work87t@gmail.com", "From support", "This is is from support");
        service.sendEmail(EmailAlia.CONTACT, "work87t@gmail.com", "From contact", "This is is from contact");
    }
}