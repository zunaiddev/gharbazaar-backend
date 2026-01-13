package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.LoginReq;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
import com.gharbazaar.backend.enums.UserStatus;

public interface AuthService {
    SignupRes signup(SignupReq signupReq);

    UserStatus login(LoginReq loginReq);
}