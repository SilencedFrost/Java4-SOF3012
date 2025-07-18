package com.dto;

import com.security.PasswordHasher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    String userId;
    String passwordHash;
    String fullName;
    String email;

    public UserDTO(String userId, String email, String fullName) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static List<List<String>> toListOfLists(List<UserDTO> users) {
        List<List<String>> result = new ArrayList<>();
        for (UserDTO user : users) {
            List<String> row = new ArrayList<>();
            row.add(user.getUserId());
            row.add(user.getPasswordHash());
            row.add(user.getFullName());
            row.add(user.getEmail());
            result.add(row);
        }
        return result;
    }

    public static List<String> getHeaders() {
        return List.of("ID", "Full Name", "Password", "Email");
    }
}
