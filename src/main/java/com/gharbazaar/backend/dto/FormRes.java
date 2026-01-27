package com.gharbazaar.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gharbazaar.backend.model.Form;

import java.time.LocalDateTime;

public record FormRes(long id, String name, String email, String phone, String subject,
                      String message, String referenceId,
                      @JsonFormat(pattern = "yyyy MMM dd HH:mm")
                      LocalDateTime submittedAt) {
    public FormRes(Form form) {
        this(form.getId(), form.getName(), form.getEmail(), form.getPhone(),
                form.getSubject(), form.getMessage(), form.getReferenceId(), form.getCreatedAt());
    }
}