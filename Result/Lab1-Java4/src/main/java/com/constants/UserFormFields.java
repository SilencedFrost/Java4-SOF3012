package com.constants;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum UserFormFields {
    USER_ID("userId", "user", "User ID", UserValidationError.USER_ID_REQUIRED, null),
    PASSWORD("passwordHash", "password", "Password", UserValidationError.PASSWORD_REQUIRED, UserValidationError.PASSWORD_INCORRECT_FORMAT),
    FULL_NAME("fullName", "fullName", "Full Name", UserValidationError.FULL_NAME_REQUIRED, null),
    EMAIL("email", "email", "Email", UserValidationError.EMAIL_REQUIRED, UserValidationError.EMAIL_INCORRECT_FORMAT),
    ROLE("admin", "admin", "Admin", UserValidationError.ROLE_REQUIRED, null);

    private final String propertyName;
    private final String fieldKey;
    private final String colName;
    private final UserValidationError requiredError;
    private final UserValidationError formatError;

    // Pre-computed list using for loop
    private static final List<String> ALL_FIELD_KEYS = initializeFieldKeys();

    UserFormFields(String propertyName, String fieldKey, String colName, UserValidationError requiredError, UserValidationError formatError) {
        this.propertyName = propertyName;
        this.fieldKey = fieldKey;
        this.colName = colName;
        this.requiredError = requiredError;
        this.formatError = formatError;
    }
    private static List<String> initializeFieldKeys() {
        UserFormFields[] values = values();
        List<String> colNames = new ArrayList<>(values.length);

        for (UserFormFields field : values) {
            colNames.add(field.getColName());
        }

        return List.copyOf(colNames); // Immutable copy
    }

    public static List<String> getAllFieldKeys() {
        return ALL_FIELD_KEYS;
    }

    public String fieldKey() {
        return fieldKey;
    }

    public String errorKey() {
        return fieldKey + "Error";
    }

}
