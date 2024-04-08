package com.farion.onlinebookstore.validator;

import com.farion.onlinebookstore.lib.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object firstFieldValue = new BeanWrapperImpl(o).getPropertyValue(firstFieldName);
        Object secondFieldValue = new BeanWrapperImpl(o).getPropertyValue(secondFieldName);
        return Objects.equals(firstFieldValue, secondFieldValue);
    }
}
