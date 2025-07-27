package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum UserFormFields implements Automatable {
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
    private final List<String> selectionData;
}
