package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareDTO {
    private Long shareId;
    private String userId;
    private String videoId;
    private String email;
    private Date shareDate;
}
