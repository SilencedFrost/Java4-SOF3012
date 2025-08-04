package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentId;
    private String userId;
    private String videoId;
    private LocalDateTime commentDate;
    private String commentContent;

    public CommentDTO(String userId, String videoId, String commentContent) {
        this.userId = userId;
        this.videoId = videoId;
        this.commentContent = commentContent;
    }

    public CommentDTO(Long commentId, String userId, String videoId, String commentContent) {
        this.commentId = commentId;
        this.userId = userId;
        this.videoId = videoId;
        this.commentContent = commentContent;
    }
}
