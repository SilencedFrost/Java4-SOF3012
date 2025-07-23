package com.mapper;

import com.dto.VideoDTO;
import com.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoMapper {

    public static VideoDTO toDTO(Video video) {
        if (video == null) return null;

        return new VideoDTO(
                video.getVideoId(),
                video.getTitle(),
                video.getPoster(),
                video.getLink(),
                video.getViews(),
                video.getDescription(),
                video.getActive()
        );
    }

    public static Video toEntity(VideoDTO videoDTO) {
        if (videoDTO == null) return null;

        return new Video(
                videoDTO.getVideoId(),
                videoDTO.getTitle(),
                videoDTO.getPoster(),
                videoDTO.getLink(),
                videoDTO.getViews(),
                videoDTO.getDescription(),
                videoDTO.getActive()
        );
    }

    public static List<VideoDTO> toDTOList(List<Video> entityList) {
        List<VideoDTO> dtoList = new ArrayList<>();
        for (Video video : entityList) dtoList.add(VideoMapper.toDTO(video));
        return dtoList;
    }
}
