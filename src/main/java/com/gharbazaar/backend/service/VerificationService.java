package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.LoginRes;
import com.gharbazaar.backend.dto.ResetPasswordReq;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.model.User;

public interface VerificationService {
    LoginRes verifyEmail(User user, Purpose purpose);

    void resetPassword(User user, ResetPasswordReq req, Purpose purpose);
}