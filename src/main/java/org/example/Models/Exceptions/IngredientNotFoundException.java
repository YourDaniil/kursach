package org.example.Models.Exceptions;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(String message) {
        super(message);
    }
    public IngredientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
