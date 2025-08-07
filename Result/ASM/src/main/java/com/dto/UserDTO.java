package com.dto;

import java.time.LocalDateTime;

public interface UserDTO {
    public String getUserId();
    public String getFullName();
    public String getEmail();
    public String getRoleName();
    public LocalDateTime getCreationDate();
}
