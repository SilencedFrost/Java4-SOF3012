package com.mapper;

import com.dto.LogDTO;
import com.entity.Log;
import com.entity.User;
import com.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class LogMapper {

    public static LogDTO toDTO(Log log) {
        if (log == null) {
            return null;
        }
        return new LogDTO(
                log.getLogId(),
                log.getLink(),
                log.getLoginTime(),
                log.getUser().getUserId()
        );
    }

    public static Log toEntity(LogDTO logDTO, User user) {
        if (logDTO == null) {
            return null;
        }

        return new Log(
                logDTO.getLink(),
                user
        );
    }

    public static List<LogDTO> toDTOList(List<Log> entityList) {
        List<LogDTO> dtoList = new ArrayList<>();
        for (Log log : entityList) {
            dtoList.add(LogMapper.toDTO(log));
        }
        return dtoList;
    }
}
