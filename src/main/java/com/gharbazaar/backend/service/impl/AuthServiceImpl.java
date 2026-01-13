package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.LoginReq;
import com.gharbazaar.backend.dto.SignupReq;
import com.gharbazaar.backend.dto.SignupRes;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public SignupRes signup(SignupReq signupReq) {
        return null;
    }

    @Override
    public UserStatus login(LoginReq loginReq) {
        return null;
    }
}
