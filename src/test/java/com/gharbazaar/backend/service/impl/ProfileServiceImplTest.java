package com.gharbazaar.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.UserRepository;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
@ActiveProfiles("db")
class ProfileServiceImplTest {
    Cloudinary cloudinary = new Cloudinary(new DotEnv().get("CLOUDINARY_URL"));
    @Autowired
    private ProfileServiceImpl service;
    @Autowired
    private UserRepository userRepo;

    @Test
    void upload() throws IOException {
        User user = userRepo.save(new User("John", "john@gmaj.dcm", "jh", UserStatus.UNVERIFIED));

        File file = new File("src/main/resources/avatars/demo.jpeg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        Profile profile = service.upload(bytes, user);
        user.setProfile(profile);
        userRepo.save(user);

        System.out.println(profile);
    }

    @Test
    void reUpload() throws IOException {
        upload();

        File file = new File("src/main/resources/avatars/demo2.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        User user = userRepo.findById(1L).orElse(null);
        Assertions.assertNotNull(user);

        Profile profile = user.getProfile();
        Assertions.assertNotNull(profile);

        Profile persistedProfile = service.findById(profile.getId());
        Assertions.assertNotNull(persistedProfile);

        service.reUpload(persistedProfile, bytes);
        System.out.println();
    }
}