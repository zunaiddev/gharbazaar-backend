package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.PasswordUpdateReq;
import com.gharbazaar.backend.dto.UserUpdateReq;
import com.gharbazaar.backend.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User create(String name, String email, String password);

    User update(User user, UserUpdateReq req);

    User update(User user);

    String updatePassword(User user, PasswordUpdateReq req);

    User save(User user);

    User findById(long id);

    User findByEmail(String email, boolean throwException);

    User findByEmail(String email);

    User uploadAvatar(User user, MultipartFile file);


    void delete(User user, String password);
}
