package com.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ShareFormFields implements Automatable {
    SHARE_ID("shareId", "Share ID", "id", null),
    USER_ID("userId", "User ID", "text", null),
    Video_ID("videoId", "Video ID", "text", null),
    RECEIVER_EMAIL("receiverEmail", "Receiver Email", "text", null),
    SHARE_DATE("shareDate", "Share Date", "text", null);

    private final String propertyKey;
    private final String label;
    private final String fieldType;
    private final List<String> selectionData;
}
