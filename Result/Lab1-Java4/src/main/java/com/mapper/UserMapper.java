package com.mapper;

import com.dto.UserDTO;
import com.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUserId(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getEmail()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new User(
                userDTO.getUserId(),
                userDTO.getPasswordHash(),
                userDTO.getFullName(),
                userDTO.getEmail()
        );
    }

    public static List<User> toEntityList(List<UserDTO> dtoList) {
        List<User> entityList = new ArrayList<>();
        for (UserDTO userDTO : dtoList) {
            entityList.add(UserMapper.toEntity(userDTO));
        }
        return entityList;
    }

    public static List<UserDTO> toDTOList(List<User> entityList) {
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : entityList) {
            dtoList.add(UserMapper.toDTO(user));
        }
        return dtoList;
    }
}
