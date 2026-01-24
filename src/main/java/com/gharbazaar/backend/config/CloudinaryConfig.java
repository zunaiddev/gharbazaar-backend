package com.gharbazaar.backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(@Value("${CLOUDINARY_URL}") String CLOUDINARY_URL) {
        return new Cloudinary(CLOUDINARY_URL);
    }
}
