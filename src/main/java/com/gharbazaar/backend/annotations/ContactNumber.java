package com.gharbazaar.backend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@Pattern(
        regexp = "^\\+[1-9]\\d{7,14}$",
        message = "Phone number must include country code and contain only digits (example: +14155552671)"
)

@Constraint(validatedBy = {})
public @interface ContactNumber {

    String message() default "Invalid contact number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}