package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.dto.VideoDTO;
import com.service.UserService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet ("/video/search")
public class VideoSearchServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VideoSearchServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTitle = req.getParameter("searchId");

        req.setAttribute("searchId", searchTitle);
        req.getRequestDispatcher("/WEB-INF/jsp/videoSearch.jsp").forward(req, resp);
    }
}
