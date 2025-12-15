package com.fullsystem.fullsystem.infrastructure.web.advice;

import com.fullsystem.fullsystem.domain.exception.ConflictException;
import com.fullsystem.fullsystem.domain.exception.NotFoundException;
import com.fullsystem.fullsystem.domain.exception.ValidationException;
import com.fullsystem.fullsystem.infrastructure.web.response.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all REST controllers.
 * Converts exceptions to appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NotFoundException (404).
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(AppResponse.error(ex.getMessage()));
    }

    /**
     * Handles ValidationException (400).
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<AppResponse<Object>> handleValidationException(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AppResponse.error(ex.getMessage()));
    }

    /**
     * Handles ConflictException (409).
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<AppResponse<Object>> handleConflictException(ConflictException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(AppResponse.error(ex.getMessage()));
    }

    /**
     * Handles Jakarta validation errors (400).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        AppResponse<Map<String, String>> response = new AppResponse<>(
                false,
                "Validation failed",
                errors,
                java.time.LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Handles all other exceptions (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse<Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AppResponse.error("An unexpected error occurred: " + ex.getMessage()));
    }
}
