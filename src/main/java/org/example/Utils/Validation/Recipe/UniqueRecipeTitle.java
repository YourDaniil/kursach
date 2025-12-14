package org.example.Utils.Validation.Recipe;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueTitleRecipeValidator.class)
public @interface UniqueRecipeTitle {
    String message() default "Recipe title exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
