package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDTO {
    private Long favouriteId;
    private String userId;
    private String videoId;
    private LocalDateTime favouriteDate;

    public FavouriteDTO(String userId, String videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }

    public FavouriteDTO(Long favouriteId, String userId, String videoId) {
        this.favouriteId = favouriteId;
        this.userId = userId;
        this.videoId = videoId;
    }
}
