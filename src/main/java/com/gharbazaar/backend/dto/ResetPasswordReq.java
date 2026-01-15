package com.gharbazaar.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordReq(
        @NotBlank(message = "password must not be null or blank")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,64}$", message = "Enter a Strong Password")
        String password
) {
}
