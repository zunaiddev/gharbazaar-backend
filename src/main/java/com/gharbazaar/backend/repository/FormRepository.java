package com.gharbazaar.backend.repository;

import com.gharbazaar.backend.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
}