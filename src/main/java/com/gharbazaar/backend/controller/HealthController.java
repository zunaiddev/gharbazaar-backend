package com.gharbazaar.backend.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<String> health() {
        try {
            entityManager.createNativeQuery("SELECT 1").getResultList();
            return ResponseEntity.ok("UP");
        } catch (Exception e) {
            return ResponseEntity.status(503).body("DOWN");
        }
    }
}
