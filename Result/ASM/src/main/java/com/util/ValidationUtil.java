package com.util;

import com.constants.UserFormFields;
import com.service.UserService;

import java.util.Map;

public class ValidationUtil {
    private static final UserService userService = new UserService();

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

    public static void validateUserId(String userId, Map<String, String> errors) {
        if(ValidationUtil.isNullOrBlank(userId)) {
            errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id cannot be empty!");
        } else if (
                userService.findById(userId) != null ||
                        userService.findByEmail(userId) != null ||
                        ValidationUtil.isValidEmail(userId)) {
            errors.put(UserFormFields.USER_ID.getErrorKey(), "Invalid user ID");
        }
    }

    public static void validatePassword(String password, Map<String, String> errors) {
        if(ValidationUtil.isNullOrBlank(password)){
            errors.put(UserFormFields.PASSWORD.getErrorKey(), "Password cannot be empty!");
        } else if (!ValidationUtil.isValidPassword(password)) {
            errors.put(UserFormFields.PASSWORD.getErrorKey(), "8-32 characters with uppercase, lowercase, number & special character");
        }
    }

    public static void validateEmail(String email, Map<String, String> errors) {
        if(ValidationUtil.isNullOrBlank(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email cannot be empty!");
        } else if (!ValidationUtil.isValidEmail(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email is invalid!");
        } else if (userService.findByEmail(email) != null) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email already taken");
        }
    }

    public static void validateFullName(String fullName, Map<String, String> errors) {
        if(ValidationUtil.isNullOrBlank(fullName)) {
            errors.put(UserFormFields.FULL_NAME.getErrorKey(), "Full name cannot be empty!");
        }
    }
}
