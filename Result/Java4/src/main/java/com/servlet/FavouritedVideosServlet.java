package com.servlet;

import com.constants.FavouriteFormFields;
import com.constants.UserFormFields;
import com.constants.VideoFormFields;
import com.dto.FavouriteDTO;
import com.service.FavouriteService;
import com.service.UserService;
import com.service.VideoService;
import com.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet ("/video/favourited")
public class FavouritedVideosServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(FavouritedVideosServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        List<Map<String, String>> dataList = new ArrayList<>();
        List<FavouriteDTO> favouriteList =  new FavouriteService().findAll();

        for(FavouriteDTO favouriteDTO : favouriteList) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), new VideoService().findById(favouriteDTO.getVideoId()).getTitle());
            dataMap.put(UserFormFields.FULL_NAME.getPropertyKey(), new UserService().findById(favouriteDTO.getUserId()).getFullName());
            dataMap.put(FavouriteFormFields.LIKE_DATE.getPropertyKey(), formatter.format(favouriteDTO.getLikeDate()));
            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, dataList, VideoFormFields.TITLE, UserFormFields.FULL_NAME, FavouriteFormFields.LIKE_DATE);
        req.getRequestDispatcher("/WEB-INF/jsp/favouritedVideos.jsp").forward(req, resp);
    }
}
