package com.constants;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum UserFormFields {
    USER_ID("userId", "User ID", "text", UserValidationError.USER_ID_REQUIRED, null),
    PASSWORD("passwordHash", "Password", "password", UserValidationError.PASSWORD_REQUIRED, UserValidationError.PASSWORD_INCORRECT_FORMAT),
    FULL_NAME("fullName", "Full Name", "text", UserValidationError.FULL_NAME_REQUIRED, null),
    EMAIL("email", "Email", "email", UserValidationError.EMAIL_REQUIRED, UserValidationError.EMAIL_INCORRECT_FORMAT),
    ROLE("roleName", "Role Name", "combobox", UserValidationError.ROLE_REQUIRED, null);

    private final String propertyKey;
    private final String label;
    private final UserValidationError requiredError;
    private final UserValidationError formatError;

    private static final List<String> ALL_FIELD_KEYS = initializePropertyKeys();

    UserFormFields(String propertyKey, String label, String fieldType, UserValidationError requiredError, UserValidationError formatError) {
        this.propertyKey = propertyKey;
        this.label = label;
        this.requiredError = requiredError;
        this.formatError = formatError;
    }
    private static List<String> initializePropertyKeys() {
        UserFormFields[] values = values();
        List<String> propertyKeys = new ArrayList<>(values.length);

        for (UserFormFields field : values) {
            propertyKeys.add(field.getPropertyKey());
        }

        return List.copyOf(propertyKeys); // Immutable copy
    }

    public static List<String> getAllPropertyKeys() {
        return ALL_FIELD_KEYS;
    }

    public String propertyKey() {
        return propertyKey;
    }

    public String errorKey() {
        return propertyKey + "Error";
    }
}
