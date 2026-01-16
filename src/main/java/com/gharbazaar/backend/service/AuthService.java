package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginRes> googleOAuth(String code);

    SignupRes signup(SignupReq signupReq);

    LoginRes login(LoginReq loginReq);

    ForgotPasswordRes forgotPassword(ForgotPasswordReq req);
}