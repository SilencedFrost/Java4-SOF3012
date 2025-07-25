package com.servlet;

import com.constants.Automatable;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.UserService;
import com.service.VideoService;
import com.util.ServletUtil;
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

@WebServlet ("/video/search")
public class VideoSearchServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VideoSearchServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTitle = req.getParameter("search");

        List<VideoDTO> videoList = null;

        if(!ValidationUtil.isNullOrBlank(searchTitle)) {
            videoList = new VideoService().findByTitleLike(searchTitle);
            if(videoList == null) videoList = List.of();
        } else {
            videoList = new VideoService().findAll();
        }

        List<Map<String, String>> dataList = new ArrayList<>();
        Automatable likeCount = new CustomFormFields("likeCount", "Like Count", "text", null);

        for(VideoDTO videoDTO : videoList) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
            Integer like = new VideoService().findShareCount(videoDTO.getVideoId());
            dataMap.put(likeCount.getPropertyKey(), like != null ? like.toString() : "0");
            dataMap.put(VideoFormFields.ACTIVE.getPropertyKey(), videoDTO.getActive().toString());
            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, dataList, VideoFormFields.TITLE, likeCount, VideoFormFields.ACTIVE);
        req.setAttribute("search", searchTitle);
        req.getRequestDispatcher("/WEB-INF/jsp/videoSearch.jsp").forward(req, resp);
    }
}
