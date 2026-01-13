package com.gharbazaar.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
        @NotBlank(message = "email must not be null or blank")
        String email,
        @NotBlank(message = "password must not be null or blank")
        String password
) {
}
