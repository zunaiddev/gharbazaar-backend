package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.OAuthClient;
import com.gharbazaar.backend.enums.Role;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;

import java.time.LocalDateTime;

public record UserRes(long id, String name, String email, String profile,
                      Role role, UserStatus status, OAuthClient authClient,
                      boolean enabled, boolean locked, LocalDateTime createdAt) {
    public UserRes(User user, Profile profile) {
        this(user.getId(), user.getName(), user.getEmail(), profile != null ? profile.getUrl() : null,
                user.getRole(), user.getStatus(), user.getOAuthClient(),
                user.isEnabled(), user.isLocked(), user.getCreatedAt());
    }

    public UserRes(User user) {
        this(user, null);
    }
}
