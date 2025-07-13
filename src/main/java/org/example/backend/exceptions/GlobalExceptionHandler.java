package org.example.backend.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DuplicateIngredientNameException.class)
        public ResponseEntity<String> handleDuplicateIngredientNameException(DuplicateIngredientNameException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause != null && cause.getMessage() != null && cause.getMessage().contains("Illegal unquoted character")) {
            return ResponseEntity.badRequest()
                    .body("Je invoer bevat een fout in de JSON-opmaak. Controleer of alle velden correct geformuleerd zijn (bijv. haakjes en aanhalingstekens).");
        }

        if (cause instanceof InvalidFormatException invalidFormat) {
            Class<?> targetType = invalidFormat.getTargetType();

            if (targetType.isEnum()) {
                String fieldName = invalidFormat.getPath().stream()
                        .map(JsonMappingException.Reference::getFieldName)
                        .reduce((a, b) -> a + "." + b)
                        .orElse("onbekend veld");

                String allowedValues = String.join(", ",
                        Arrays.stream(targetType.getEnumConstants())
                                .map(Object::toString)
                                .toList());

                return ResponseEntity.badRequest()
                        .body("Ongeldige waarde voor veld '" + fieldName + "': '" + invalidFormat.getValue()
                                + "'. Kies uit: " + allowedValues + ".");
            }
        }

        return ResponseEntity.badRequest()
                .body("De invoer kon niet verwerkt worden. Controleer of de JSON geldig is en alle velden correct zijn ingevuld.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            String allowedValues = Arrays.stream(ex.getRequiredType().getEnumConstants())
                    .map(Object::toString)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            return ResponseEntity.badRequest()
                    .body("Ongeldige waarde voor parameter '" + ex.getName() + "': '" + ex.getValue()
                            + "'. Kies uit: " + allowedValues + ".");
        }
        return ResponseEntity.badRequest()
                .body("Ongeldige waarde voor parameter '" + ex.getName() + "': '" + ex.getValue() + "'.");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception e) {
        return new ResponseEntity<>("Er ging iets mis: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
