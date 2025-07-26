package com.servlet;

import com.dto.VideoDTO;
import com.service.VideoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/home"}
)
public class HomePageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HomePageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoDTO> videoList = new VideoService().findAll();

        req.setAttribute("videoList", videoList);

        req.getRequestDispatcher("/WEB-INF/jsp/homepage.jsp").forward(req, resp);
    }
}
