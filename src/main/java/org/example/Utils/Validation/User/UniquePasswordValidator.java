package org.example.Utils.Validation.User;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.Repositories.UserRepository;

public class UniquePasswordValidator implements ConstraintValidator<UniquePassword, String> {
    private final UserRepository userRepository;

    public UniquePasswordValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override // проверяем на одинаковые имена
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByPassword(value).isEmpty();
    }
}
