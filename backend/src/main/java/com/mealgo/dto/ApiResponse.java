package com.mealgo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(
            String message,
            T data) {

        return new ApiResponse<>(
                true,
                message,
                data);
    }

    public static ApiResponse<Void> success(
            String message) {

        return new ApiResponse<>(
                true,
                message,
                null);
    }

    public static ApiResponse<Void> fail(
            String message) {

        return new ApiResponse<>(
                false,
                message,
                null);
    }

    public static <T> ApiResponse<T> fail(
            String message,
            T data) {

        return new ApiResponse<>(
                false,
                message,
                data);
    }
}