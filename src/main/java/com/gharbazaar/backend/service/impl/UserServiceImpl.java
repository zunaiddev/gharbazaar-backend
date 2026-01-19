package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.UserRepository;
import com.gharbazaar.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
//        file.transferTo();
        return null;
    }
}