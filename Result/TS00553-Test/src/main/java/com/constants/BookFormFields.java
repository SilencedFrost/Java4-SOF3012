package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum BookFormFields implements Automatable {
    BOOK_ID("bookId", "Book ID", "id", null),
    TITLE("title", "Title", "text", null),
    PRICE("price", "Price", "text", null),
    AUTHOR_ID("authorId", "Author Id", "text", null);

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
