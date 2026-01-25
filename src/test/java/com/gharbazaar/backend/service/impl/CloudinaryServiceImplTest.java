package com.gharbazaar.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.gharbazaar.backend.dto.CloudinaryRes;
import com.gharbazaar.backend.enums.CloudinaryFolder;
import com.gharbazaar.backend.service.CloudinaryService;
import com.gharbazaar.backend.utils.DotEnv;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class CloudinaryServiceImplTest {
    Cloudinary cloudinary = new Cloudinary(new DotEnv().get("CLOUDINARY_URL"));
    CloudinaryService cloudinaryService = new CloudinaryServiceImpl(cloudinary);

    @Test
    void upload() throws IOException {
        File file = new File("src/main/resources/avatars/demo.jpeg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        CloudinaryRes res = cloudinaryService.upload(bytes, CloudinaryFolder.PROFILES);
        System.out.println(res);
    }

    @Test
    void delete() {
        cloudinaryService.delete("profiles/file_vzbesg");
        System.out.println("Deleted");
    }
}