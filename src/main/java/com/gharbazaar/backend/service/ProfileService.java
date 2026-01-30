package com.gharbazaar.backend.service;

import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;

public interface ProfileService {
    Profile findById(long id);

    Profile findByUser(User user);

    Profile save(Profile profile);

    Profile update(Profile profile);

    Profile upload(byte[] bytes, User user);

    Profile reUpload(Profile profile, byte[] bytes);

    void delete(Profile profile);
}