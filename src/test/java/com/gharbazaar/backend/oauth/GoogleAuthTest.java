package com.gharbazaar.backend.oauth;

import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class GoogleAuthTest {
    private final GoogleAuth auth;

    public GoogleAuthTest() {
        DotEnv dotEnv = new DotEnv();

        auth = new GoogleAuth(new RestTemplate(),
                dotEnv.get("GOOGLE_CLIENT_ID"),
                dotEnv.get("GOOGLE_CLIENT_SECRET"),
                dotEnv.get("GOOGLE_REDIRECT_URI"));
    }

    @Test
    void getAccessToken() {
        OAuthUser user = auth.getOAuthUser("4/0ASc3gC2KPmXMEz8hY7Wz7TlT-Gg82cdjvSJ1cV-pYqAqC_yS7B9WNITpvCYJMj7faXWYQg");
        System.out.println(user);
    }
}