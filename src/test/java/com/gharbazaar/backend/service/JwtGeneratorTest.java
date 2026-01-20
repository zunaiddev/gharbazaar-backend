package com.gharbazaar.backend.service;

import com.gharbazaar.backend.enums.Role;
import com.gharbazaar.backend.helpers.TestHelper;
import com.gharbazaar.backend.service.impl.JwtServiceImpl;
import com.gharbazaar.backend.utils.JwtGenerator;
import org.junit.jupiter.api.Test;

class JwtGeneratorTest {
    private final JwtGenerator jwtGenerator;

    public JwtGeneratorTest() {
        JwtService service = new JwtServiceImpl(TestHelper.JWT_SECRET);
        jwtGenerator = new JwtGenerator(service);
        System.out.println(TestHelper.JWT_SECRET);
    }

    @Test
    void generateTokens() {
        String authentication = jwtGenerator.authentication(1L, Role.USER);
        String refresh = jwtGenerator.refresh(1L, Role.USER);
        String verification = jwtGenerator.verification(1L, Role.USER);
        String resetPassword = jwtGenerator.resetPassword(1L, Role.USER);
        String reactivate = jwtGenerator.reactivate(1L, Role.USER);

        System.out.println("Authentication Token:\n" + authentication);
        System.out.println("Refresh Token:\n" + refresh);
        System.out.println("Verification Token:\n" + verification);
        System.out.println("Reset Password Token:\n" + resetPassword);
        System.out.println("Reactivate Token:\n" + reactivate);
    }
}