package com.servlet;

import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/user/favourite"}
)
public class UserFavouriteServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserFavouriteServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<VideoDTO> videoList = new UserService().findFavouritedVideos(((UserDTO) session.getAttribute("user")).getUserId());

        req.setAttribute("videoList", videoList);
        req.getRequestDispatcher("/WEB-INF/jsp/userFavourite.jsp").forward(req, resp);
    }
}
