package com.farion.onlinebookstore.validator;

import com.farion.onlinebookstore.lib.CategoryIds;
import com.farion.onlinebookstore.service.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryIdsValidator implements ConstraintValidator<CategoryIds, Set<Long>> {
    @Setter
    private Set<Long> allCategoryIds;
    private final CategoryService categoryService;

    @Override
    public void initialize(CategoryIds constraintAnnotation) {
        allCategoryIds = categoryService.getAllCategoryIds();
    }

    @Override
    public boolean isValid(
            Set<Long> categoryIds,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return allCategoryIds.containsAll(categoryIds);
    }

}
