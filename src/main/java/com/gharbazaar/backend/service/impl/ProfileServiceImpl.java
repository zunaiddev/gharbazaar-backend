package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.CloudinaryRes;
import com.gharbazaar.backend.enums.CloudinaryFolder;
import com.gharbazaar.backend.model.Profile;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.repository.ProfileRepository;
import com.gharbazaar.backend.service.CloudinaryService;
import com.gharbazaar.backend.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repo;
    private final CloudinaryService cloudinaryService;


    @Override
    public Profile findById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with id: " + id));
    }

    @Override
    public Profile findByUserId(long id) {
        return repo.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with user id: " + id));
    }

    @Override
    public Profile save(Profile profile) {
        if (profile.getId() != null) throw new IllegalStateException("Profile id is auto generated");

        return repo.save(profile);
    }

    @Override
    public Profile update(Profile profile) {
        if (profile.getId() == null) throw new IllegalStateException("Profile id is null");

        return repo.save(profile);
    }

    @Override
    public Profile upload(byte[] bytes, User user) {
        CloudinaryRes res = cloudinaryService.upload(bytes, CloudinaryFolder.PROFILES);

        return this.save(new Profile(res.url(), res.publicId(), user));
    }

    public Profile reUpload(Profile profile, byte[] bytes) {
        CloudinaryRes res = cloudinaryService.upload(bytes, CloudinaryFolder.PROFILES);
        cloudinaryService.deleteAsync(profile.getPublicId());

        profile.setUrl(res.url());
        profile.setPublicId(res.publicId());
        return this.update(profile);
    }

    @Override
    public void delete(Profile profile) {

    }
}