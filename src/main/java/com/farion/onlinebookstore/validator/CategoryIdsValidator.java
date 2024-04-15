package com.farion.onlinebookstore.validator;

import com.farion.onlinebookstore.lib.CategoryIds;
import com.farion.onlinebookstore.service.CategoryService;
import com.farion.onlinebookstore.util.ConditionHolder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryIdsValidator implements ConstraintValidator<CategoryIds, Set<Long>> {
    private final ConditionHolder conditionHolder;
    private final CategoryService categoryService;

    @Override
    public void initialize(CategoryIds constraintAnnotation) {
        conditionHolder.setCondition(ThreadLocal.withInitial(categoryService::getAllCategoryIds));
    }

    @Override
    public boolean isValid(
            Set<Long> categoryIds,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return conditionHolder.getCondition().get().containsAll(categoryIds);
    }
}
