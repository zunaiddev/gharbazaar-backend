package com.gharbazaar.backend.service;

import com.gharbazaar.backend.model.User;

public interface UserService {
    User create(String name, String email, String password);

    User findByEmail(String email, boolean throwException);

    User findByEmail(String email);
}
