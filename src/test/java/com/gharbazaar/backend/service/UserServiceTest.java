package com.gharbazaar.backend.service;

import com.gharbazaar.backend.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @Order(1)
    void create() {
        userService.create("John Doe", "john@gmail.com", "john@123");
        userService.create("John Doe", "john@gmail.com", "john@123");
        System.out.println("User created");
    }

    @Test
    @Order(2)
    void findByEmail() {
        User user = userService.findByEmail("john@gmail.com");

        Assertions.assertNotNull(user);
        System.out.println("User Fetched");
        System.out.println(user);
    }
}