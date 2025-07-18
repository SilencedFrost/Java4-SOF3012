package com.constants;

public enum UserValidationError {
    USER_ID_REQUIRED("User ID is required."),
    PASSWORD_REQUIRED("Password is required."),
    PASSWORD_INCORRECT_FORMAT(
            "Passwords need to be:\n" +
                    "8 characters long\n" +
                    "Have one lowercase letter\n" +
                    "Have one uppercase letter\n" +
                    "Have one special character"),
    FULL_NAME_REQUIRED("Full name is required"),
    EMAIL_REQUIRED("Email is required."),
    EMAIL_INCORRECT_FORMAT("Please input a valid email.");

    private final String message;

    UserValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
