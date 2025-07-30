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
public class ShareDTO {
    private Long shareId;
    private String userId;
    private String videoId;
    private String receiveEmail;
    private LocalDateTime shareDate;

    public ShareDTO(String userId, String videoId, String receiveEmail) {
        this.userId = userId;
        this.videoId = videoId;
        this.receiveEmail = receiveEmail;
    }

    public ShareDTO(Long shareId, String userId, String videoId, String receiveEmail) {
        this.shareId = shareId;
        this.userId = userId;
        this.videoId = videoId;
        this.receiveEmail = receiveEmail;
    }
}
