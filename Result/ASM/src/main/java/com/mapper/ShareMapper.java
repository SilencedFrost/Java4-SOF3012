package com.mapper;

import com.dto.ShareDTO;
import com.entity.Share;
import com.entity.User;
import com.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class ShareMapper {

    public static ShareDTO toDTO(Share share) {
        if (share == null) {
            return null;
        }
        return new ShareDTO(
                share.getShareId(),
                share.getUser().getUserId(),
                share.getVideo().getVideoId(),
                share.getReceiveEmail(),
                share.getShareDate()
        );
    }

    public static Share toEntity(ShareDTO shareDTO, User user, Video video) {
        if (shareDTO == null) {
            return null;
        }

        return new Share(
                user,
                video,
                shareDTO.getReceiveEmail(),
                shareDTO.getShareDate()
        );
    }

    public static List<ShareDTO> toDTOList(List<Share> entityList) {
        List<ShareDTO> dtoList = new ArrayList<>();
        for (Share share : entityList) {
            dtoList.add(ShareMapper.toDTO(share));
        }
        return dtoList;
    }
}
