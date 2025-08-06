package com.servlet;

import com.constants.VideoFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
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

        for(VideoDTO videoDTO : videoList) {
            if(videoDTO.getActive()){
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
        }

        req.setAttribute("dataList", dataList);
        req.getRequestDispatcher("/WEB-INF/jsp/homepage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String favourite = req.getParameter("favourite");
        String unfavourite = req.getParameter("unfavourite");
        String share = req.getParameter("share");
        String email = req.getParameter("email");

        HttpSession session = req.getSession(false);
        if(session == null) {resp.sendRedirect("/home"); return;}

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if((!ValidationUtil.isNullOrBlank(favourite) || !ValidationUtil.isNullOrBlank(unfavourite) || !ValidationUtil.isNullOrBlank(share)) && userDTO == null) {
            resp.sendRedirect("/login");
            return;
        }

        if(!ValidationUtil.isNullOrBlank(favourite)){
            new FavouriteService().create(userDTO.getUserId(), favourite);
        } else if(!ValidationUtil.isNullOrBlank(unfavourite)) {
            new FavouriteService().delete(userDTO.getUserId(), unfavourite);
        } else if(!ValidationUtil.isNullOrBlank(share) && ValidationUtil.isValidEmail(email)) {
            new ShareService().create(userDTO.getUserId(), share, email);
        }

        resp.sendRedirect("/home");
    }
}
