package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.FormReq;
import com.gharbazaar.backend.dto.FormRes;
import com.gharbazaar.backend.service.FormService;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("db")
class FormServiceImplTest {
    @Autowired
    private FormService formService;

    @BeforeAll
    static void init() {
        DotEnv.load();
    }

    @Test
    void submit() {
        FormReq form = new FormReq("John", "Email@gmail.com",
                "+919690578859", "Test subject", "This is a testing message");

        FormRes res = formService.submit(form);

        System.out.println(res);
    }
}