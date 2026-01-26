package com.gharbazaar.backend.enums;

import lombok.Getter;

@Getter
public enum EmailAlia {
    NO_REPLY("noreply@gharbazaar.in"),
    CONTACT("contact@gharbazaar.in"),
    SUPPORT("support@gharbazaar.in");

    private final String alias;

    EmailAlia(String alias) {
        this.alias = alias;
    }
}