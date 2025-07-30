package com.mapper;

import com.dto.FavouriteDTO;
import com.entity.Favourite;
import com.entity.User;
import com.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMapper {

    public static FavouriteDTO toDTO(Favourite favourite) {
        if (favourite == null) {
            return null;
        }
        return new FavouriteDTO(
                favourite.getFavouriteId(),
                favourite.getUser().getUserId(),
                favourite.getVideo().getVideoId(),
                favourite.getFavouriteDate()
        );
    }

    public static Favourite toEntity(User user, Video video) {
        return new Favourite(
                user,
                video
        );
    }

    public static List<FavouriteDTO> toDTOList(List<Favourite> entityList) {
        List<FavouriteDTO> dtoList = new ArrayList<>();
        for (Favourite favourite : entityList) {
            dtoList.add(FavouriteMapper.toDTO(favourite));
        }
        return dtoList;
    }
}
