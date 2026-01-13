package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.model.User;

public record SignupRes(long id, String name, String email) {
    public SignupRes(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
