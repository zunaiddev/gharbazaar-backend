package com.gharbazaar.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerificationController {

    @PostMapping("/email")
    public String verifyEmail(String token) {
        return "OK";
    }
}
