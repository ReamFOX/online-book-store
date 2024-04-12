package com.farion.onlinebookstore.lib;

import com.farion.onlinebookstore.validator.CategoryIdsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryIdsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryIds {
    String message() default "Invalid category IDs";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
