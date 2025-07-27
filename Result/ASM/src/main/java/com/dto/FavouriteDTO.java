package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDTO {
    private Long favouriteId;
    private String userId;
    private String videoId;
    private Date favouriteDate;
}
