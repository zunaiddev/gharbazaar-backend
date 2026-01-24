package com.gharbazaar.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gharbazaar.backend.exception.CloudinaryException;
import com.gharbazaar.backend.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String upload(byte[] bytes) {
        Map<String, Object> options = Map.of(
                "use_filename", true
        );

        try {
            Map response = cloudinary.uploader().upload(bytes, options);
            System.out.println(response);

            return response.get("url").toString();
        } catch (IOException e) {
            throw new CloudinaryException("Could Not Upload File");
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new CloudinaryException("Could Not Upload File");
        }
    }

    @Async
    @Override
    public void deleteAsync(String publicId) {
        delete(publicId);
    }
}
