package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ProductFormFields implements Automatable {
    PRODUCT_ID("productId", "Product ID", "id", null),
    PRODUCT_NAME("productName", "Product Name", "text", null),
    PRICE("price", "Price", "text", null),
    CATEGORY_ID("categoryId", "Category Id", "text", null);

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
