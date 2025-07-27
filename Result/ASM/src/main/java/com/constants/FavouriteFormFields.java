package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum FavouriteFormFields implements Automatable {
    FAVOURITE_ID("favouriteId", "Favourite ID", "text", null),
    USER_ID("userId", "User ID", "text", null),
    VIDEO_ID("videoId", "video ID", "text", null),
    FAVOURITE_DATE("favouriteDate", "Favourite Date", "text", null);

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
