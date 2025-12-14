package org.example.Utils.Validation.Ingredient;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.Repositories.IngredientRepository;
import org.example.Repositories.RecipeRepository;

import java.util.concurrent.ConcurrentMap;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    private final IngredientRepository ingredientRepository;

    public UniqueNameValidator(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override // прошел проверку - true, иначе - false
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ingredientRepository.findByName(value).isEmpty();
    }
}
