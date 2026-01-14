package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.model.User;

public interface VerificationService {
    LoginRes verifyEmail(User user);
}