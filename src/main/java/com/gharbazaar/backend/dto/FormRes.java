package com.gharbazaar.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gharbazaar.backend.model.Form;

import java.time.Instant;

public record FormRes(long id, String name, String email, String phone, String subject,
                      String message,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                      Instant submittedAt) {
    public FormRes(Form form) {
        this(form.getId(), form.getName(), form.getEmail(), form.getPhone(),
                form.getSubject(), form.getMessage(), form.getCreatedAt());
    }
}
