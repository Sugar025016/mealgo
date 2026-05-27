package com.mealgo.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName) {
        super("找不到" + resourceName);
    }
}