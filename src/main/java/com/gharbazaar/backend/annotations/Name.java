package com.gharbazaar.backend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "name must not be null or blank")
@Pattern(regexp = "^[A-Za-z][A-Za-z .-]{1,49}$",
        message = "Name must be 2 to 50 characters long and can contain only letters, spaces, dots, or hyphens")

@Constraint(validatedBy = {})
public @interface Name {
    String message() default "Invalid name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}