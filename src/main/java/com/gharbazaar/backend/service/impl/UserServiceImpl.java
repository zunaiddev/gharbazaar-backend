package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.exception.InvalidFileTypeException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.UserRepository;
import com.gharbazaar.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

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
        if (!Set.of("/jpeg", "/jpg", "/png").contains(file.getContentType())) {
            throw new InvalidFileTypeException("Invalid file type");
        }

        try {
            Path path = Paths.get("src/main/resources/avatars");

            if (Files.notExists(path)) Files.createDirectories(path);

            file.transferTo(path.resolve(user.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}