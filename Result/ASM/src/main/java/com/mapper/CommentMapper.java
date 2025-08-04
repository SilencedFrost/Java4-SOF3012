package com.mapper;

import com.dto.CommentDTO;
import com.entity.Comment;
import com.entity.User;
import com.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentDTO(
                comment.getCommentId(),
                comment.getUser().getUserId(),
                comment.getVideo().getVideoId(),
                comment.getCommentDate(),
                comment.getCommentContent()
        );
    }

    public static Comment toEntity(CommentDTO commentDTO, User user, Video video) {
        if (commentDTO == null) {
            return null;
        }

        return new Comment(
                user,
                video,
                commentDTO.getCommentContent()
        );
    }

    public static List<CommentDTO> toDTOList(List<Comment> entityList) {
        List<CommentDTO> dtoList = new ArrayList<>();
        for (Comment comment : entityList) {
            dtoList.add(CommentMapper.toDTO(comment));
        }
        return dtoList;
    }
}
