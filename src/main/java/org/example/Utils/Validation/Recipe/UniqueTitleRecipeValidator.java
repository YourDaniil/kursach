package org.example.Utils.Validation.Recipe;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.Repositories.RecipeRepository;

public class UniqueTitleRecipeValidator implements ConstraintValidator<UniqueRecipeTitle, String> {
    private final RecipeRepository recipeRepository;

    public UniqueTitleRecipeValidator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override // прошел проверку - true, иначе - false
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return recipeRepository.findByTitle(value).isEmpty();
    }
}
