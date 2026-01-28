package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.PasswordUpdateReq;
import com.gharbazaar.backend.dto.UserUpdateReq;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.UserRepository;
import com.gharbazaar.backend.service.ProfileService;
import com.gharbazaar.backend.service.UserService;
import com.gharbazaar.backend.utils.Helper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final ProfileService profileService;
    private final PasswordEncoder encoder;

    @Override
    public User create(String name, String email, String password) {
        final User user = findByEmail(email, false);

        if (user != null) {
            if (user.isEnabled()) {
                throw new ConflictException("User already exists");
            }

            user.setName(name);
            user.setPassword(password);
            user.setCreatedAt(LocalDateTime.now());

            return repo.save(user);
        }

        return repo.save(new User(name, email, password, UserStatus.UNVERIFIED));
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) throw new IllegalStateException("User ID must be provided while updating");

        return repo.save(user);
    }

    @Override
    public User update(User user, UserUpdateReq req) {
        user.setName(req.name());
        return this.update(user);
    }

    @Override
    public String updatePassword(User user, PasswordUpdateReq req) {
        if (req.password().equals(req.newPassword())) {
            throw new ConflictException("New password cannot be the same as the old password");
        }

        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        if (encoder.matches(req.newPassword(), user.getPassword())) {
            throw new ConflictException("Please choose a password that is different from your current password.");
        }

        user.setPassword(encoder.encode(req.newPassword()));
        this.update(user);

        return "Password updated successfully.";
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) throw new IllegalStateException("User id is auto generated");

        return repo.save(user);
    }

    @Override
    public User findById(long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email, boolean throwException) {
        User user = repo.findByEmail(email).orElse(null);

        if (user == null && throwException) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return findByEmail(email, true);
    }

    @Override
    public User uploadAvatar(User user, MultipartFile file) {
        validateProfile(file);

        Profile profile = user.getProfile();

        try {
            if (user.getProfile() == null) {
                user.setProfile(profileService.upload(file.getBytes(), user));
                return this.update(user);
            }

            user.setProfile(profileService.reUpload(profileService.findById(profile.getId()), file.getBytes()));
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user, String password) {

    }

    private void validateProfile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MultipartException("File is empty");
        }

        if (file.getSize() > Helper.maxFileSize) {
            throw new MultipartException("File size exceeds the limit of " + Helper.maxFileSize + " bytes");
        }

        if (!Helper.isImage(file.getContentType())) {
            throw new MultipartException("Invalid file type. Only jpeg, jpg and png are allowed");
        }
    }
}