package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.annotations.Name;

public record UserUpdateReq(
        @Name
        String name
) {

}
