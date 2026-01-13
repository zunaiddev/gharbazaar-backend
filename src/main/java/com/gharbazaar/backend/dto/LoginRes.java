package com.gharbazaar.backend.dto;

import com.gharbazaar.backend.enums.UserStatus;

public record LoginRes(String token, UserStatus status) {

}
