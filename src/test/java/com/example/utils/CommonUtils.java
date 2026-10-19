package com.example.utils;

public final class CommonUtils {

    private CommonUtils() {
        // prevent instantiation
    }

    public static String randomEmail() {
        return "user" + System.currentTimeMillis() + "@example.com";
    }
}