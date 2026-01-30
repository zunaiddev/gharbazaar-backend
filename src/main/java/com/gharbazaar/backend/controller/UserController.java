package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.PasswordUpdateReq;
import com.gharbazaar.backend.dto.UserRes;
import com.gharbazaar.backend.dto.UserUpdateReq;
import com.gharbazaar.backend.security.UserPrincipal;
import com.gharbazaar.backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public UserRes getInfo(@AuthenticationPrincipal UserPrincipal details) {
        return service.getInfo(details.user());
    }

    @PatchMapping
    public UserRes updateName(@AuthenticationPrincipal UserPrincipal details, @RequestBody @Valid UserUpdateReq req) {
        return new UserRes(service.update(details.user(), req));
    }

    @PatchMapping("/password")
    public String updatePassword(@AuthenticationPrincipal UserPrincipal details, @RequestBody @Valid PasswordUpdateReq req) {
        return service.updatePassword(details.user(), req);
    }

    @PutMapping("/profile")
    public UserRes uploadAvatar(@AuthenticationPrincipal UserPrincipal details,
                                @RequestParam @NotNull MultipartFile file) {
        System.out.println(details.user());
        return new UserRes(service.uploadAvatar(details.user(), file));
    }
}
