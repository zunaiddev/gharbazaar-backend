package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.helpers.TestHelper;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.oauth.GoogleAuth;
import com.gharbazaar.backend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
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

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private GoogleAuth googleAuth;

    @MockitoBean
    private HttpServletResponse response;

    @MockitoBean
    private EmailService emailService;

    // ToDo: decode token for each res and add a method to the helper class to validate all the tokens
    @Test
    @Order(1)
    void googleOAuth() {
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        when(googleAuth.getOAuthUser("testCode"))
                .thenReturn(new OAuthUser("AuthUser", "auth@gmail.com", "test", true));

        // First case: where the user sings up manually and then Sings up with the same Google account without verifying his email
        service.signup(new SignupReq("AuthUser", "auth@gmail.com", "Temp@123"));

        ResponseEntity<LoginRes> res = service.googleOAuth("testCode", this.response);

        assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(201));

        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();
        TestHelper.printCookie(cookie);
        System.out.println("Login Response: " + res.getBody());

        // Second case: A Fresh User
        when(googleAuth.getOAuthUser("freshCode"))
                .thenReturn(new OAuthUser("Fresh user", "freshuser@gmail.com", "fresh", true));

        ResponseEntity<LoginRes> res2 = service.googleOAuth("freshCode", this.response);
        assertEquals(res2.getStatusCode(), HttpStatusCode.valueOf(201));

        // Third case: User login
        ResponseEntity<LoginRes> res3 = service.googleOAuth("freshCode", this.response);
        assertEquals(res3.getStatusCode(), HttpStatusCode.valueOf(200));

        // Fourth case: User is Locked
        User user = userRepo.findByEmail("freshuser@gmail.com").orElse(null);
        assertNotNull(user);
        user.setLocked(true);
        userRepo.save(user);

        assertThrowsExactly(LockedException.class, () -> service.googleOAuth("freshCode", this.response));

        // Fifth case: User status is PENDING_DELETE
        user.setLocked(false);
        user.setStatus(UserStatus.PENDING_DELETE);
        userRepo.save(user);

        ResponseEntity<LoginRes> res5 = service.googleOAuth("freshCode", this.response);
        assertEquals(res5.getStatusCode(), HttpStatusCode.valueOf(200));

        LoginRes res5Body = res5.getBody();
        assertNotNull(res5Body);
        assertEquals(UserStatus.PENDING_DELETE, res5Body.status());
    }

    @Test
    @Order(2)
    void signup() {
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

        // First case: User already exists
        ConflictException exception = assertThrowsExactly(ConflictException.class, () -> {
            service.signup(new SignupReq("John", "auth@gmail.com", "Pass@123"));
        });
        System.out.println("Message: " + exception.getMessage());

        // Second case: A Fresh User Signs up
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