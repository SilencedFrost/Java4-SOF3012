package com.mapper;

import com.dto.InboundUserDTO;
import com.dto.OutboundUserDTO;
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
        return new OutboundUserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getRoleName() : "null",
                user.getCreationDate()
        );
    }

    public static User toEntity(InboundUserDTO userDTO, Role role) {
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
