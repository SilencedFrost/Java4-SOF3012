package com.servlet;

import com.constants.Automatable;
import com.constants.CustomFormFields;
import com.constants.VideoFormFields;
import com.dto.VideoDTO;
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

@WebServlet ("/video/sharedetails")
public class VideoShareDetailsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VideoShareDetailsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");

        List<VideoDTO> videoList = new VideoService().findAll();
        if(videoList == null) videoList = List.of();

        List<Map<String, String>> dataList = new ArrayList<>();
        Automatable shareCount = new CustomFormFields("shareCount", "Share Count", "text", null);
        Automatable firstShareDate = new CustomFormFields("firstShareDate", "First Share Date", "text", null);
        Automatable lastShareDate = new CustomFormFields("lastShareDate", "Last Share Date", "text", null);

        for(VideoDTO videoDTO : videoList) {
            Map<String, String> dataMap = new HashMap<>();

            dataMap.put(VideoFormFields.TITLE.getPropertyKey(), videoDTO.getTitle());
            Integer share = new VideoService().findShareCount(videoDTO.getVideoId());
            dataMap.put(shareCount.getPropertyKey(), share != null ? share.toString() : "0");
            List<Date> dateList = new VideoService().findAllShareDates(videoDTO.getVideoId());
            String firstDate = null;
            String lastDate = null;
            if(dateList != null && !dateList.isEmpty()) {
                Collections.sort(dateList);
                firstDate = formatter.format(dateList.getFirst());
                lastDate = formatter.format(dateList.getLast());
            }
            dataMap.put(firstShareDate.getPropertyKey(), firstDate != null ? firstDate : "no data");
            dataMap.put(lastShareDate.getPropertyKey(), lastDate != null ? lastDate : "no data");
            dataList.add(dataMap);
        }

        ServletUtil.setTableData(req, dataList, VideoFormFields.TITLE, shareCount, firstShareDate, lastShareDate);
        req.getRequestDispatcher("/WEB-INF/jsp/videoShareDetails.jsp").forward(req, resp);
    }
}
