package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.utils.NameFormatter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import tools.jackson.databind.annotation.JsonDeserialize;

public record SignupReq(
        @NotBlank(message = "name must not be null or blank")
        @Pattern(regexp = "^[A-Za-z][A-Za-z .-]{1,49}$", message = "name must be between 2 to 50 characters and must be a valid name")
        @JsonDeserialize(using = NameFormatter.class)
        String name,

        @NotBlank(message = "email must not be null or blank")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,189}\\.[A-Za-z]{2,}$", message = "please enter a valid email")
        String email,

        @NotBlank(message = "password must not be null or blank")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,64}$", message = "Enter a Strong Password")
        String password
) {
}
