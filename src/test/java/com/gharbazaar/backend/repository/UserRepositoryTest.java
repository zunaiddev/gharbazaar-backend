package com.gharbazaar.backend.repository;

import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = new User("John Doe", "john@gmail.com", "John@123", UserStatus.ACTIVE);
        userRepository.save(user);

        User persisted = userRepository.findByEmail("john@gmail.com").orElse(null);

        assertNotNull(persisted);

        System.out.println(persisted);
    }
}