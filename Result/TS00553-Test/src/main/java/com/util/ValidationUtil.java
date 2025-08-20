package com.util;

public class ValidationUtil {
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isBlank();
    }
}
