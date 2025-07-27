package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonFormFields implements Buttons{
    CREATE("create", "Create", "primary"),
    UPDATE("update", "Update", "warning"),
    DELETE("delete", "Delete", "danger"),
    RESET("reset", "Reset", "secondary"),
    LOGIN("login", "Login", "primary"),
    REGISTER("register", "Register", "primary"),
    SUBMIT("submit", "Submit", "primary");

    private final String propertyKey;
    private final String label;
    private final String BSColor;
}
