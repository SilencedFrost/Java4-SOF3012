/* package com.dto;

import com.entity.Favorite;
import com.entity.Share;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private String videoId;
    private String title;
    private String poster;
    private long views;
    private String description;
    private boolean active;

    public static List<List<String>> toListOfLists(List<VideoDTO> videos) {
        List<List<String>> result = new ArrayList<>();
        for (VideoDTO video : videos) {
            List<String> row = new ArrayList<>();
            row.add(video.getUserId());
            row.add(video.getPasswordHash());
            row.add(video.getFullName());
            row.add(video.getEmail());
            row.add(video.getAdmin() ? "Admin" : "User");
            result.add(row);
        }
        return result;
    }
} */
