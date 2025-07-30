package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private String videoId;
    private String title;
    private String thumbnail;
    private String link;
    private Long views;
    private String description;
    private Boolean active;
}
