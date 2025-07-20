package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String passwordHash;
    private String fullName;
    private String email;
    private Boolean admin = false;

    public UserDTO(String userId, String passwordHash, String fullName, String email){
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + (admin ? "Admin" : "User") + '\'' +
                '}';
    }
}
