package com.gharbazaar.backend.repository;

import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);

    Optional<Profile> findByUrl(String url);
}