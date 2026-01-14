package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.model.UsedToken;
import com.gharbazaar.backend.repository.UsedTokenRepository;
import com.gharbazaar.backend.service.UsedTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedTokenServiceImpl implements UsedTokenService {
    private final UsedTokenRepository repo;

    @Override
    public void save(String token) {
        repo.save(new UsedToken(null, token));
    }

    @Override
    public boolean exists(String token) {
        return repo.existsByToken(token);
    }
}
