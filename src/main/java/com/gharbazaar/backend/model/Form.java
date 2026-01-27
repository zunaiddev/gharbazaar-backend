package com.gharbazaar.backend.model;

import com.gharbazaar.backend.dto.FormReq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Table(name = "forms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Form(FormReq req) {
        this.name = req.name();
        this.email = req.email();
        this.subject = req.subject();
        this.phone = req.phone();
        this.message = req.message();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}