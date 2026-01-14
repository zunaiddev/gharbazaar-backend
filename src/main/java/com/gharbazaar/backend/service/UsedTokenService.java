package com.gharbazaar.backend.service;

public interface UsedTokenService {
    void save(String token);

    boolean exists(String token);
}
