package com.servlet;

import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
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

@WebServlet ({"/video/watch"})
public class WatchVideoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(WatchVideoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        VideoService videoService = new VideoService();

        // =================================================

        List<VideoDTO> favouritedVideos = List.of();

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        String userId = "guest";
        if(userDTO != null) {userId = userDTO.getUserId(); favouritedVideos = new UserService().findFavouritedVideos(userDTO);}

        String videoId = req.getParameter("id");
        if(ValidationUtil.isNullOrBlank(videoId)) {resp.sendRedirect("/home"); return;}

        VideoDTO videoDTO = videoService.findById(videoId);

        if(videoDTO == null) {resp.sendRedirect("/home"); return;}

        Map<String, Set<String>> watchedVideos = (HashMap<String, Set<String>>) session.getAttribute("watchedVideos");
        if(watchedVideos == null) {
            watchedVideos = new HashMap<>();
            session.setAttribute("watchedVideos", watchedVideos);
        }

        Set<String> userWatched = watchedVideos.getOrDefault(userId, new HashSet<>());

        if(!userWatched.contains(videoId)) {
            videoService.incrementView(videoId);
            userWatched.add(videoId);
            watchedVideos.put(userId, userWatched);
        }

        videoDTO = videoService.findById(videoId);

        Map<String, String> videoData = new HashMap<>();
        for(VideoDTO video: favouritedVideos) {
            if(video.getVideoId().equals(videoId)) videoData.put("isFavourited", "favourited");
        }

        videoData.put(VideoFormFields.VIDEO_ID.getPropertyKey(), videoDTO.getVideoId());
        videoData.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
        videoData.put(VideoFormFields.POSTER.getPropertyKey(), videoDTO.getPoster());
        videoData.put(VideoFormFields.VIEWS.getPropertyKey(), videoDTO.getViews().toString());
        videoData.put(VideoFormFields.DESCRIPTION.getPropertyKey(), videoDTO.getDescription());

        req.setAttribute("video", videoData);

        // ========================================

        List<VideoDTO> videoList = videoService.findAll();

        List<Map<String, String>> dataList = new ArrayList<>();

        videoList.sort(Comparator.comparingLong(VideoDTO::getViews).reversed());

        for(VideoDTO video : videoList) {
            if(videoDTO.getActive() && !video.getVideoId().equals(videoDTO.getVideoId())){
                Map<String, String> dataMap = new HashMap<>();

                dataMap.put(VideoFormFields.VIDEO_ID.getPropertyKey(), video.getVideoId());
                dataMap.put(VideoFormFields.TITLE.getPropertyKey(), video.getTitle());
                dataMap.put(VideoFormFields.POSTER.getPropertyKey(), video.getPoster());
                dataMap.put(VideoFormFields.VIEWS.getPropertyKey(), video.getViews().toString());

                dataList.add(dataMap);
            }
        }

        req.setAttribute("dataList", dataList);

        req.getRequestDispatcher("/WEB-INF/jsp/watchVideo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String favourite = req.getParameter("favourite");
        String unfavourite = req.getParameter("unfavourite");

        HttpSession session = req.getSession();

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

        String videoId = !ValidationUtil.isNullOrBlank(favourite) ? favourite : unfavourite;

        resp.sendRedirect("/video/watch?id=" + videoId);
    }
}
