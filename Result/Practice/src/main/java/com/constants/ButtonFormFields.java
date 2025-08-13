package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonFormFields implements Buttons{
    CREATE("create", "Create", "primary", "POST"),
    UPDATE("update", "Update", "warning", "PUT"),
    DELETE("delete", "Delete", "danger", "DELETE"),
    RESET("reset", "Reset", "secondary", null),
    LOGIN("login", "Login", "primary", "POST"),
    REGISTER("register", "Register", "primary", "POST"),
    SUBMIT("submit", "Submit", "primary", "POST");

    private final String propertyKey;
    private final String label;
    private final String BSColor;
    private final String submitMethod;
}
