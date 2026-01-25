package com.gharbazaar.backend.model;

import com.gharbazaar.backend.enums.OAuthClient;
import com.gharbazaar.backend.enums.Role;
import com.gharbazaar.backend.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    //    private String phone;
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OAuthClient oAuthClient;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;

    @NotNull
    private boolean enabled;

    @NotNull
    private boolean locked;

    private LocalDateTime deleteAt;

    @NotNull
    private LocalDateTime createdAt;

    public User(String name, String email, String password, UserStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.enabled = false;
        this.locked = false;
        role = Role.USER;
        oAuthClient = OAuthClient.NONE;
        deleteAt = null;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}