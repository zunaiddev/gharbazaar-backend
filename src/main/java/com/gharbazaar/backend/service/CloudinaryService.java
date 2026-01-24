package com.gharbazaar.backend.service;

public interface CloudinaryService {
    String upload(byte[] bytes);

    void delete(String publicId);

    void deleteAsync(String publicId);
}
