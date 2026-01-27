package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.annotations.Email;
import com.gharbazaar.backend.annotations.Name;
import com.gharbazaar.backend.annotations.Password;
import com.gharbazaar.backend.utils.NameFormatter;
import tools.jackson.databind.annotation.JsonDeserialize;

public record SignupReq(
        @Name
        @JsonDeserialize(using = NameFormatter.class)
        String name,

        @Email
        String email,

        @Password
        String password
) {
}
