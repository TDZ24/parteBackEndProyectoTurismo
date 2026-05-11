package com.tuapp.reservasturismo.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        List<ApiError> errors,
        LocalDateTime timestamp,
        String path,
        Integer status
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null, LocalDateTime.now(), null, null);
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null, null, LocalDateTime.now(), null, null);
    }

    public static ApiResponse<Void> error(String message, List<ApiError> errors, String path, Integer status) {
        return new ApiResponse<>(false, message, null, errors, LocalDateTime.now(), path, status);
    }
}
