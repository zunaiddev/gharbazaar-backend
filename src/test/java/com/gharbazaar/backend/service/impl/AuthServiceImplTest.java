package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.oauth.GoogleAuth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authService;

    @MockitoBean
    private GoogleAuth googleAuth;

    @Test
    void googleOAuth() {
        Mockito.when(googleAuth.getOAuthUser("code"))
                .thenReturn(new OAuthUser("John", "john@gmail.com", "sample", true));

        ResponseEntity<LoginRes> res = authService.googleOAuth("code", null);

        System.out.println("Status: " + res.getStatusCode());
    }
}