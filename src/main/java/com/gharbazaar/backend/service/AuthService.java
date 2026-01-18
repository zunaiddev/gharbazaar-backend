package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginRes> googleOAuth(String code, HttpServletResponse res);

    SignupRes signup(SignupReq signupReq);

    LoginRes login(LoginReq loginReq, HttpServletResponse res);

    void logout(HttpServletResponse res);

    ForgotPasswordRes forgotPassword(ForgotPasswordReq req);
}