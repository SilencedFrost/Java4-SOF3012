package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum CategoryFormFields implements Automatable {
    CATEGORY_ID("categoryId", "Category ID", "id", null),
    CATEGORY_NAME("categoryName", "Category Name", "text", null);


    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
