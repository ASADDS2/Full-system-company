package com.fullsystem.fullsystem.infrastructure.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generic wrapper for API responses.
 * Provides consistent response structure across all endpoints.
 *
 * @param <T> the type of data being returned
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    /**
     * Creates a successful response with data.
     * 
     * @param data    the response data
     * @param message the success message
     * @param <T>     the type of data
     * @return the response wrapper
     */
    public static <T> AppResponse<T> success(T data, String message) {
        return new AppResponse<>(true, message, data, LocalDateTime.now());
    }

    /**
     * Creates a successful response with default message.
     * 
     * @param data the response data
     * @param <T>  the type of data
     * @return the response wrapper
     */
    public static <T> AppResponse<T> success(T data) {
        return success(data, "Operation completed successfully");
    }

    /**
     * Creates an error response.
     * 
     * @param message the error message
     * @param <T>     the type of data
     * @return the response wrapper
     */
    public static <T> AppResponse<T> error(String message) {
        return new AppResponse<>(false, message, null, LocalDateTime.now());
    }
}
