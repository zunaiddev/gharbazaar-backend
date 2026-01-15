package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.*;

public interface AuthService {
    SignupRes signup(SignupReq signupReq);

    LoginRes login(LoginReq loginReq);

    ForgotPasswordRes forgotPassword(ForgotPasswordReq req);
}