package com.mapper;

import com.dto.UserDTO;
import com.entity.Role;
import com.entity.User;
import com.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUserId(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().getRoleName()
        );
    }

    public static User toEntity(UserDTO userDTO, Role role) {
        if (userDTO == null) {
            return null;
        }

        return new User(
                userDTO.getUserId(),
                userDTO.getPasswordHash(),
                userDTO.getFullName(),
                userDTO.getEmail(),
                role
        );
    }

    public static List<UserDTO> toDTOList(List<User> entityList) {
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : entityList) {
            dtoList.add(UserMapper.toDTO(user));
        }
        return dtoList;
    }
}
