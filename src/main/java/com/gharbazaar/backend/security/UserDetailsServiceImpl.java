package com.gharbazaar.backend.security;

import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("Returned User details");
        return new UserPrincipal(user);
    }
}
