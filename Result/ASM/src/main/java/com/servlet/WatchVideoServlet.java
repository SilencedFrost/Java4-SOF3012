package com.servlet;

import com.dto.VideoDTO;
import com.service.VideoService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet ({"/video/watch"})
public class WatchVideoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(WatchVideoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");

        VideoDTO videoDTO = null;

        if(!ValidationUtil.isNullOrBlank(videoId)) {
            videoDTO = new VideoService().findById(videoId);
        } else {
            resp.sendRedirect("/home");
            return;
        }

        req.setAttribute("video", videoDTO);
        req.getRequestDispatcher("/WEB-INF/jsp/watchVideo.jsp").forward(req, resp);
    }
}
