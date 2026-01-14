package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.UserRes;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public UserRes getInfo(@AuthenticationPrincipal UserPrincipal details) {
        return new UserRes(details.user());
    }
}
