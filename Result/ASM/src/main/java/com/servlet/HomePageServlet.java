package com.servlet;

import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.email.EmailSender;
import com.service.FavouriteService;
import com.service.ShareService;
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
import java.util.*;
import java.util.logging.Logger;

@WebServlet ("/home")
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

        VideoService videoService = new VideoService();

        if(!ValidationUtil.isNullOrBlank(searchVideo)) {
            videoList = videoService.findByTitleLike(searchVideo);
        } else {
            videoList = videoService.findAll();
        }

        videoList.removeIf(videoDTO -> videoDTO.getActive() == null || !videoDTO.getActive());

        //Sort
        videoList.sort(Comparator.comparingLong(VideoDTO::getViews).reversed());

        //Paginate
        int itemsPerPage = 12;

        req.setAttribute("pageCount", (int) Math.ceil((double) videoList.size() / itemsPerPage));

        String pageString = req.getParameter("page");
        int page = 1;
        if(pageString != null) {
            page = Integer.parseInt(pageString);
        }

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, videoList.size());

        videoList = videoList.subList(startIndex, endIndex);

        List<Map<String, String>> dataList = getMaps(videoList, favouritedVideos);

        req.setAttribute("dataList", dataList);

        req.getRequestDispatcher("/WEB-INF/jsp/homepage.jsp").forward(req, resp);
    }

    private static List<Map<String, String>> getMaps(List<VideoDTO> videoList, List<VideoDTO> favouritedVideos) {
        List<Map<String, String>> dataList = new ArrayList<>();

        for(VideoDTO videoDTO : videoList) {
            Map<String, String> dataMap = new HashMap<>();

            for(VideoDTO favouritedVideo : favouritedVideos) {
                if(favouritedVideo.getVideoId().equals(videoDTO.getVideoId())) dataMap.put("isFavourited", "favourited");
            }

            dataMap.put(VideoFormFields.VIDEO_ID.getPropertyKey(), videoDTO.getVideoId());
            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
            dataMap.put(VideoFormFields.POSTER.getPropertyKey(), videoDTO.getThumbnail());
            dataMap.put(VideoFormFields.VIEWS.getPropertyKey(), videoDTO.getViews().toString());

            dataList.add(dataMap);
        }

        return dataList;
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
        } else if(!ValidationUtil.isNullOrBlank(unfavourite)) {
            new FavouriteService().delete(userDTO.getUserId(), unfavourite);
        }

        resp.sendRedirect("/home");
    }
}
