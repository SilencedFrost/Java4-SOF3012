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
public class LogDTO {

    private Long logId;
    private String link;
    private LocalDateTime loginTime;
    private String userId;

    public LogDTO(String link, String userId) {
        this.link = link;
        this.userId = userId;
    }

    public LogDTO(Long logId, String link, String userId) {
        this.logId = logId;
        this.link = link;
        this.userId = userId;
    }
}
