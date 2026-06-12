package com.sgic.Expense.Tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler centralizes all error handling.
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * It intercepts exceptions thrown from ANY controller and converts them
 * into clean, consistent JSON error responses.
 *
 * Without this, Spring Boot would return HTML error pages or raw exception
 * stack traces to API clients — which is neither useful nor secure.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Bean Validation failures (@Valid annotation on request bodies).
     * Returns a map of field name → error message.
     *
     * Example response body:
     * {
     *   "email": "Please provide a valid email address",
     *   "password": "Password must be at least 6 characters"
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(
                HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors
        ));
    }

    /**
     * Handles 404 Not Found — resource doesn't exist in DB.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage(), null)
        );
    }

    /**
     * Handles wrong email/password during login.
     * Returns 401 Unauthorized (not 403 — the user is simply not authenticated).
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(HttpStatus.UNAUTHORIZED, "Invalid email or password", null)
        );
    }

    /**
     * Handles Spring Security's UsernameNotFoundException.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), null)
        );
    }

    /**
     * Handles 403 Forbidden — user is authenticated but lacks permission.
     * E.g., a USER trying to call an ADMIN-only endpoint.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                buildError(HttpStatus.FORBIDDEN, "Access denied: insufficient permissions", null)
        );
    }

    /**
     * Handles duplicate email registration.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                buildError(HttpStatus.CONFLICT, ex.getMessage(), null)
        );
    }

    /**
     * Catch-all handler — prevents stack traces from leaking to clients.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null)
        );
    }

    // -------------------------------------------------------------------------
    // Helper to build a consistent error response structure
    // -------------------------------------------------------------------------
    private Map<String, Object> buildError(HttpStatus status, String message, Object details) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        if (details != null) {
            error.put("details", details);
        }
        return error;
    }
}
