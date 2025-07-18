package com.constants;

import lombok.Getter;

public enum UserFormFields {
    USER_ID("userId", UserValidationError.USER_ID_REQUIRED, null),
    PASSWORD("password", UserValidationError.PASSWORD_REQUIRED, UserValidationError.PASSWORD_INCORRECT_FORMAT),
    FULL_NAME("fullName", UserValidationError.FULL_NAME_REQUIRED, null),
    EMAIL("email", UserValidationError.EMAIL_REQUIRED, UserValidationError.EMAIL_INCORRECT_FORMAT);

    private final String fieldKey;
    @Getter
    private final UserValidationError requiredError;
    @Getter
    private final UserValidationError formatError;

    UserFormFields(String fieldKey, UserValidationError requiredError, UserValidationError formatError) {
        this.fieldKey = fieldKey;
        this.requiredError = requiredError;
        this.formatError = formatError;
    }

    public String fieldKey() {
        return fieldKey;
    }

    public String errorKey() {
        return fieldKey + "Error";
    }

}
