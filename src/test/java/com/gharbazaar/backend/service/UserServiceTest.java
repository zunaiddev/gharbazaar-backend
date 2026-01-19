package com.gharbazaar.backend.service;

import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.exception.ConflictException;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.oauth.GoogleAuth;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @Autowired
    private UserService service;

    @MockitoBean
    private GoogleAuth googleAuth;

    @Test
    @Order(1)
    void create() {
        User user = service.create("test user", "test@gmail.com", "Test@123");
        assertNotNull(user);
        System.out.println("User1: " + user);

        User user2 = service.create("Changed", "test@gmail.com", "Changed@123");
        assertNotNull(user2);
        System.out.println("User2: " + user2);

        user2.setEnabled(true);
        user2.setStatus(UserStatus.ACTIVE);
        service.update(user2);
        System.out.println("User2 updated: " + user2);

        ConflictException exception = assertThrowsExactly(ConflictException.class, () ->
                service.create("Old User", "test@gmail.com", "Old@123"));

        System.out.println("Message: " + exception.getMessage());

        // Reverse To the previous User state for upcoming tests
        user2.setEnabled(false);
        user2.setStatus(UserStatus.UNVERIFIED);
        service.update(user2);
    }

    @Test
    @Order(2)
    void update() {
        IllegalStateException exception = assertThrowsExactly(IllegalStateException.class, () -> {
            User newUser = new User("john",
                    "john@gmail.com",
                    "John@123",
                    UserStatus.UNVERIFIED);
            service.update(newUser);
        });

        System.out.println("Message: " + exception.getMessage());

        User user = service.findByEmail("test@gmail.com");
        assertNotNull(user);

        System.out.println("User Found: " + user);

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);


        User updated = service.update(user);
        assertNotNull(updated);

        System.out.println("User Updated: " + updated);
    }

    @Test
    @Order(3)
    void save() {
        User savedUser = service.findByEmail("test@gmail.com");

        IllegalStateException exception = assertThrowsExactly(IllegalStateException.class, () ->
                service.save(savedUser));
        System.out.println("Message: " + exception.getMessage());

        User user = new User("test2", "test2@gmail.com", "Test2@123", UserStatus.UNVERIFIED);
        assertNotNull(service.save(user));
        System.out.println("User Saved: " + user);
    }

    @Test
    @Order(4)
    void findById() {
        EntityNotFoundException exception = assertThrowsExactly(EntityNotFoundException.class,
                () -> service.findById(999L));
        System.out.println("Message: " + exception.getMessage());


        User user = service.findById(2L);
        assertNotNull(user);

        System.out.println("User Found By ID(2): " + user);
    }

    @Test
    @Order(5)
    void findByEmail() {
        EntityNotFoundException exception = assertThrowsExactly(EntityNotFoundException.class,
                () -> service.findByEmail("unknown@gmail.com"));
        System.out.println("Message: " + exception.getMessage());

        User unknownUser = service.findByEmail("unknown@gmail.com", false);
        assertNull(unknownUser);

        User user = service.findByEmail("test2@gmail.com", false);
        assertNotNull(user);

        System.out.println("User Found By Email(test2@gmail.com): " + user);
    }
}