package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.annotations.ContactNumber;
import com.gharbazaar.backend.annotations.Email;
import com.gharbazaar.backend.annotations.Name;
import com.gharbazaar.backend.utils.NameFormatter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.annotation.JsonDeserialize;

public record FormReq(
        @Name
        @JsonDeserialize(using = NameFormatter.class)
        String name,

        @Email
        String email,

        @ContactNumber
        String phone,

        @NotBlank(message = "Subject is required")
        @Size(min = 3, max = 150,
                message = "Subject must be between {min} and {max} characters")
        String subject,

        @NotBlank(message = "Message is required")
        @Size(
                min = 10,
                max = 2000,
                message = "Message must be between 10 and 2000 characters"
        )
        String message
) {
}