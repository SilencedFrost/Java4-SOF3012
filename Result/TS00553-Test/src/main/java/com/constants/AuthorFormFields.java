package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum AuthorFormFields implements Automatable {
    AUTHOR_ID("authorId", "Author ID", "id", null),
    AUTHOR_NAME("authorName", "Author Name", "text", null);


    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
