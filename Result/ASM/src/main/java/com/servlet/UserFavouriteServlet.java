package com.servlet;

import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.FavouriteService;
import com.service.UserService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@WebServlet ("/user/favourite")
public class UserFavouriteServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserFavouriteServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        String searchVideo = req.getParameter("search");
        if(searchVideo == null) searchVideo = "";

        List<VideoDTO> videoList = new UserService().findFavouritedVideos(userDTO);

        videoList.removeIf(videoDTO -> videoDTO.getActive() == null || !videoDTO.getActive());

        // Sort
        videoList.sort(Comparator.comparingLong(VideoDTO::getViews).reversed());

        // Convert to map
        List<Map<String, String>> dataList = getMaps(videoList, searchVideo);

        req.setAttribute("dataList", dataList);
        req.setAttribute("search", searchVideo);

        req.getRequestDispatcher("/WEB-INF/jsp/userFavourite.jsp").forward(req, resp);
    }

    private static List<Map<String, String>> getMaps(List<VideoDTO> videoList, String searchVideo) {
        List<Map<String, String>> dataList = new ArrayList<>();

        for(VideoDTO videoDTO : videoList) {
            if(videoDTO.getTitle().contains(searchVideo)){
                Map<String, String> dataMap = new HashMap<>();

                dataMap.put("isFavourited", "favourited");
                dataMap.put(VideoFormFields.VIDEO_ID.getPropertyKey(), videoDTO.getVideoId());
                dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
                dataMap.put(VideoFormFields.POSTER.getPropertyKey(), videoDTO.getThumbnail());
                dataMap.put(VideoFormFields.VIEWS.getPropertyKey(), videoDTO.getViews().toString());

                dataList.add(dataMap);
            }
        }

        return dataList;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String unfavourite = req.getParameter("unfavourite");

        HttpSession session = req.getSession(false);
        if(session == null) {resp.sendRedirect("/user/favourite"); return;}

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (!ValidationUtil.isNullOrBlank(unfavourite)) {
            if(userDTO != null) {
                new FavouriteService().delete(userDTO.getUserId(), unfavourite);
            } else {
                session.setAttribute("targetUrl", req.getRequestURL().toString() + (req.getQueryString() != null ? "?" + req.getQueryString() : ""));
                resp.sendRedirect("/login");
                return;
            }
        }
        resp.sendRedirect("/user/favourite");
    }
}
