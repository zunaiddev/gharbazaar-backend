package com.gharbazaar.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ForgotPasswordReq(
        @NotBlank(message = "email must not be null or blank")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,189}\\.[A-Za-z]{2,}$", message = "Invalid Email")
        String email
) {
}