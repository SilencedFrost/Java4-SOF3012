package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.UserService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet ("/user/favourite")
public class UserFavouriteServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserFavouriteServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchUserId = req.getParameter("searchId");
        List<UserDTO> users = null;

        if(!ValidationUtil.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null) users = List.of();
        } else {
            users = userService.findAll();
        }

        List<Map<String, Object>> dataList = new ArrayList<>();

        for(UserDTO userDTO : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put(UserFormFields.FULL_NAME.getPropertyKey(), userDTO.getFullName() + " - " + userDTO.getUserId());
            List<VideoDTO> videoList = new UserService().findFavouritedVideos(userDTO);
            List<String> videoNameList =  new ArrayList<>();

            for(VideoDTO videoDTO: videoList) videoNameList.add(videoDTO.getTitle());

            userMap.put("videoNameList", videoNameList);

            dataList.add(userMap);
        }

        req.setAttribute("dataList", dataList);
        req.setAttribute("searchId", searchUserId);
        req.getRequestDispatcher("/WEB-INF/jsp/userFavourite.jsp").forward(req, resp);
    }
}
