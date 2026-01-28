package com.gharbazaar.backend.oauth;

import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoogleAuthTest {
    @Autowired
    private GoogleAuth googleAuth;

    @BeforeAll
    static void init() {
        DotEnv.load();
    }

    @Test
    void getOAuthUser() {
        OAuthUser user = googleAuth
                .getOAuthUser("4/0ASc3gC3RUnPNFX0tVQWl-19DpjQzaAH8QQcMUoIVfFymWqQBNE2cz8M-GjrYeVM5X2he9A");
        System.out.println(user);
    }
}