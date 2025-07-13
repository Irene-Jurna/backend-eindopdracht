package org.example.backend.exceptions;

public class DuplicateIngredientNameException extends RuntimeException {
    public DuplicateIngredientNameException(String message) {
        super(message);
    }
}
