package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.CloudinaryRes;
import com.gharbazaar.backend.enums.CloudinaryFolder;

public interface CloudinaryService {
    CloudinaryRes upload(byte[] bytes, CloudinaryFolder folder);

    CloudinaryRes upload(byte[] bytes);

    void delete(String publicId);

    void deleteAsync(String publicId);
}
