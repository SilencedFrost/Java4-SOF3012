package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDTO {
    private Long favouriteId;
    private String userId;
    private String videoId;
    private LocalDate favouriteDate;

    public FavouriteDTO(String userId, String videoId) {
        this.userId = userId;
        this.videoId = videoId;
        this.favouriteDate = LocalDate.now();
    }
}
