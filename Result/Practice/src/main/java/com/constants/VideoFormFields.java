package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum VideoFormFields implements Automatable {
    VIDEO_ID("videoId", "Video ID", "id", null),
    TITLE("title", "Title", "text", null),
    POSTER("poster", "Poster", "text", null),
    LINK("link", "Link", "text", null),
    VIEWS("views", "Views", "text", null),
    DESCRIPTION("description", "Description", "textarea", null),
    ACTIVE("active", "Active", "radio", List.of("Active", "Inactive"));


    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
