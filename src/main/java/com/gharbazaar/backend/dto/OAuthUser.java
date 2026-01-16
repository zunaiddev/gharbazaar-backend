package com.gharbazaar.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthUser(String name, String email, String picture,
                        @JsonProperty("email_verified") boolean verified) {
}