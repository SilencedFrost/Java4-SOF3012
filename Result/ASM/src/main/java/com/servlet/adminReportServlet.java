package com.servlet;

import com.constants.*;
import com.dto.FavouriteDTO;
import com.dto.ShareDTO;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.FavouriteService;
import com.service.ShareService;
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
import java.util.*;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/admin/reports"}
)
public class adminReportServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(adminReportServlet.class.getName());
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoDTO> videoList = new VideoService().findAll();

        buildForm(req);
        buildForm2(req);
        buildForm3(req);

        req.getRequestDispatcher("/WEB-INF/jsp/adminReport.jsp").forward(req, resp);
    }

    private void buildForm(HttpServletRequest req) {
        List<VideoDTO> videoList = new VideoService().findAll();
        List<Map<String, String>> dataList = new ArrayList<>();

        if(videoList == null) videoList = List.of();

        Automatable favouriteCount = new CustomFormFields("favouriteCount", "Favourite Count", "text", null);
        Automatable firstFavouriteDate = new CustomFormFields("firstFavouriteDate", "First Favourite Date", "text", null);
        Automatable lastFavouriteDate = new CustomFormFields("lastFavouriteDate", "Last Favourite Date", "text", null);

        for(VideoDTO videoDTO : videoList) {
            Map<String, String> dataMap = new HashMap<>();

            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
            Integer favourite = new VideoService().findFavouriteCount(videoDTO.getVideoId());
            dataMap.put(favouriteCount.getPropertyKey(), favourite != null ? favourite.toString() : "0");
            List<Date> dateList = new VideoService().findAllFavouriteDates(videoDTO.getVideoId());
            String firstDate = null;
            String lastDate = null;
            if(dateList != null && !dateList.isEmpty()) {
                Collections.sort(dateList);
                firstDate = formatter.format(dateList.getFirst());
                lastDate = formatter.format(dateList.getLast());
            }
            dataMap.put(firstFavouriteDate.getPropertyKey(), firstDate != null ? firstDate : "no data");
            dataMap.put(lastFavouriteDate.getPropertyKey(), lastDate != null ? lastDate : "no data");

            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, dataList, VideoFormFields.TITLE, favouriteCount, firstFavouriteDate, lastFavouriteDate);
    }

    private void buildForm2(HttpServletRequest req) {
        List<FavouriteDTO> favouriteList =  new FavouriteService().findAll();
        List<Map<String, String>> dataList = new ArrayList<>();

        if(favouriteList == null) favouriteList = List.of();

        for(FavouriteDTO favouriteDTO : favouriteList) {
            Map<String, String> dataMap = new HashMap<>();

            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), new VideoService().findById(favouriteDTO.getVideoId()).getTitle());
            UserDTO userDTO = new UserService().findById(favouriteDTO.getUserId());
            dataMap.put(UserFormFields.FULL_NAME.getPropertyKey(), userDTO.getFullName());
            dataMap.put(UserFormFields.EMAIL.getPropertyKey(), userDTO.getEmail());
            dataMap.put(FavouriteFormFields.FAVOURITE_DATE.getPropertyKey(), formatter.format(favouriteDTO.getFavouriteDate()));

            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, 1, dataList, UserFormFields.FULL_NAME, UserFormFields.EMAIL, FavouriteFormFields.FAVOURITE_DATE);
    }

    private void buildForm3(HttpServletRequest req) {
        List<ShareDTO> shareList =  new ShareService().findAll();
        List<Map<String, String>> dataList = new ArrayList<>();

        if(shareList == null) shareList = List.of();

        for(ShareDTO shareDTO : shareList) {
            Map<String, String> dataMap = new HashMap<>();

            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), new VideoService().findById(shareDTO.getVideoId()).getTitle());
            UserDTO userDTO = new UserService().findById(shareDTO.getUserId());
            dataMap.put(UserFormFields.FULL_NAME.getPropertyKey(), userDTO.getFullName());
            dataMap.put(UserFormFields.EMAIL.getPropertyKey(), userDTO.getEmail());
            dataMap.put(ShareFormFields.RECEIVER_EMAIL.getPropertyKey(), shareDTO.getReceiveEmail());
            dataMap.put(ShareFormFields.SHARE_DATE.getPropertyKey(), formatter.format(shareDTO.getShareDate()));

            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, 2, dataList, UserFormFields.FULL_NAME, UserFormFields.EMAIL, ShareFormFields.RECEIVER_EMAIL, ShareFormFields.SHARE_DATE);
    }
}
