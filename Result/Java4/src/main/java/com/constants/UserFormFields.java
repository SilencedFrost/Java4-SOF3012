package com.constants;

import com.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum UserFormFields {
    USER_ID("userId", "User ID", "text", UserValidationError.USER_ID_REQUIRED, null, null),
    PASSWORD("passwordHash", "Password", "password", UserValidationError.PASSWORD_REQUIRED, UserValidationError.PASSWORD_INCORRECT_FORMAT, null),
    FULL_NAME("fullName", "Full Name", "text", UserValidationError.FULL_NAME_REQUIRED, null, null),
    EMAIL("email", "Email", "email", UserValidationError.EMAIL_REQUIRED, UserValidationError.EMAIL_INCORRECT_FORMAT, null),
    ROLE("roleName", "Role Name", "combobox", UserValidationError.ROLE_REQUIRED, null, List.of("User", "Employee", "Manager", "Admin"));

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final UserValidationError requiredError;
    private final UserValidationError formatError;
    private final List<String> cBoxData;

    private static final List<String> ALL_FIELD_KEYS = initializePropertyKeys();

    UserFormFields(String propertyKey, String label, String fieldType, UserValidationError requiredError, UserValidationError formatError, List<String> cBoxData) {
        this.propertyKey = propertyKey;
        this.label = label;
        this.fieldType = fieldType;
        this.requiredError = requiredError;
        this.formatError = formatError;
        this.cBoxData = cBoxData;
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
