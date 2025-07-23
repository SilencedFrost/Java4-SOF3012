package com.util;

public class ValidationUtil {
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isBlank();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;

        // At least 8 characters, one uppercase, one lowercase, one number, one special character
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }
}
