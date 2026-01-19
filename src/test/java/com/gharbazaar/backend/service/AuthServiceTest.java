package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.helpers.TestHelper;
import com.gharbazaar.backend.oauth.GoogleAuth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {
    @Autowired
    private AuthService service;

    @MockitoBean
    private GoogleAuth googleAuth;

    @MockitoBean
    private HttpServletResponse response;

    @MockitoBean
    private EmailService emailService;

    @Test
    @Order(1)
    void googleOAuth() {
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        when(googleAuth.getOAuthUser(ArgumentMatchers.anyString()))
                .thenReturn(new OAuthUser("AuthUser", "auth@gmail.com", "test", true));


        ResponseEntity<LoginRes> res = service.googleOAuth("testCode", this.response);

        assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(201));

        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();

        TestHelper.printCookie(cookie);

        System.out.println("Login Response: " + res.getBody());
    }

    @Test
    @Order(2)
    void signup() {
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

        assertThrowsExactly(ConflictException.class, () -> {
            service.signup(new SignupReq("John", "auth@gmail.com", "Pass@123"));
        });

        SignupRes res = service.signup(new SignupReq("John2", "john2@gmail.com", "Pass@123"));
        assertNotNull(res);

        verify(emailService).sendEmail(emailCaptor.capture(), emailCaptor.capture(), anyString());
        System.out.println("Email Info: " + emailCaptor.getAllValues());

        System.out.println("Signup Response: " + res);
    }

    @Test
    @Order(3)
    void login() {

    }

    @Test
    @Order(4)
    void logout() {
    }

    @Test
    @Order(5)
    void forgotPassword() {
    }
}