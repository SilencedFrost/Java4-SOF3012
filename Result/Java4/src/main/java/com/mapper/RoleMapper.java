package com.mapper;

import com.dto.RoleDTO;
import com.dto.UserDTO;
import com.entity.Role;
import com.entity.User;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDTO(
                role.getRoleId(),
                role.getRoleName()
        );
    }

    public static Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }

        return new Role(
                roleDTO.getRoleId(),
                roleDTO.getRoleName()
        );
    }

    public static List<RoleDTO> toDTOList(List<Role> entityList) {
        List<RoleDTO> dtoList = new ArrayList<>();
        for (Role role : entityList) {
            dtoList.add(RoleMapper.toDTO(role));
        }
        return dtoList;
    }
}
