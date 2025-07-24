package com.constants;

import lombok.Getter;

import java.util.List;

@Getter
public enum FavouriteFormFields implements Automatable {
    FAVOURITE_ID("favouriteId", "Favourite ID", "text", null),
    USER_ID("userId", "User ID", "text", null),
    VIDEO_ID("videoId", "video ID", "text", null),
    LIKE_DATE("likeDate", "Like Date", "text", null);


    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> cBoxData;

    FavouriteFormFields(String propertyKey, String label, String fieldType, List<String> cBoxData) {
        this.propertyKey = propertyKey;
        this.label = label;
        this.fieldType = fieldType;
        this.cBoxData = cBoxData;
    }
}
