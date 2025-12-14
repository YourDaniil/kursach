package org.example.Utils.Validation.Category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.Repositories.CategoryRepository;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {
    private final CategoryRepository categoryRepository;

    public UniqueCategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override // прошел проверку - true, иначе - false
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categoryRepository.findByName(value).isEmpty();
    }
}
