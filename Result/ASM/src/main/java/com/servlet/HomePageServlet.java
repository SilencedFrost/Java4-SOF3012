package com.servlet;

import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.entity.Favourite;
import com.service.FavouriteService;
import com.service.UserService;
import com.service.VideoService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/home"}
)
public class HomePageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HomePageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<VideoDTO> favouritedVideos = List.of();

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null) favouritedVideos = new UserService().findFavouritedVideos(userDTO);

        String searchVideo = req.getParameter("search");
        if(searchVideo == null) searchVideo = "";

        List<VideoDTO> videoList;
        List<Map<String, String>> dataList = new ArrayList<>();

        VideoService videoService = new VideoService();

        if(!ValidationUtil.isNullOrBlank(searchVideo)) {
            videoList = videoService.findByTitleLike(searchVideo);
        } else {
            videoList = videoService.findAll();
        }

        videoList.sort(Comparator.comparingLong(VideoDTO::getViews).reversed());

        for(VideoDTO videoDTO : videoList) {
            if(videoDTO.getActive()){
                Map<String, String> dataMap = new HashMap<>();

                for(VideoDTO favouritedVideo : favouritedVideos) {
                    if(favouritedVideo.getVideoId().equals(videoDTO.getVideoId())) dataMap.put("isFavourited", "favourited");
                }

                dataMap.put(VideoFormFields.VIDEO_ID.getPropertyKey(), videoDTO.getVideoId());
                dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
                dataMap.put(VideoFormFields.POSTER.getPropertyKey(), videoDTO.getPoster());
                dataMap.put(VideoFormFields.VIEWS.getPropertyKey(), videoDTO.getViews().toString());

                dataList.add(dataMap);
            }
        }

        req.setAttribute("dataList", dataList);
        req.setAttribute("search", searchVideo);
        req.getRequestDispatcher("/WEB-INF/jsp/homepage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String favourite = req.getParameter("favourite");
        String unfavourite = req.getParameter("unfavourite");

        HttpSession session = req.getSession(false);
        if(session == null) {resp.sendRedirect("/home"); return;}

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if((!ValidationUtil.isNullOrBlank(favourite) || !ValidationUtil.isNullOrBlank(unfavourite)) && userDTO == null) {
            resp.sendRedirect("/login");
            return;
        }

        if(!ValidationUtil.isNullOrBlank(favourite)){
            new FavouriteService().create(userDTO.getUserId(), favourite);
        } else if (!ValidationUtil.isNullOrBlank(unfavourite)) {
            new FavouriteService().delete(userDTO.getUserId(), unfavourite);
        }

        resp.sendRedirect("/home");
    }
}
