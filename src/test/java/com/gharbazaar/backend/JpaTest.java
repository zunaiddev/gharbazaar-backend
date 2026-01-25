package com.gharbazaar.backend;

import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.ProfileRepository;
import com.gharbazaar.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("db")
public class JpaTest {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProfileRepository profileRepo;

    @Test
    @Transactional
    public void test() {
        User user = new User("John",
                "john@gmail.com", "", UserStatus.UNVERIFIED);

        User savedUser = userRepo.save(user);

        Profile profile = new Profile(null, "url", "public id", savedUser);
        profileRepo.save(profile);

        savedUser.setProfile(profile);
        userRepo.save(savedUser);

        User fetched = userRepo.findById(savedUser.getId()).orElse(null);

        Profile profile1 = fetched.getProfile();
        System.out.println(profile1);
    }
}
