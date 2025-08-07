package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InboundUserDTO implements UserDTO{
    private String userId;
    private String passwordHash;
    private String fullName;
    private String email;
    private String roleName;
    private LocalDateTime creationDate;

    public InboundUserDTO(String userId, String passwordHash, String fullName, String email, String roleName) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.roleName = roleName;
    }
}
