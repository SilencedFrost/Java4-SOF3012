package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.security.PasswordHasher;
import com.service.UserService;
import com.util.ServletUtil;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet (
    urlPatterns = {"/userfavourite"}
)
public class UserFavouriteServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserFavouriteServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchUserId = req.getParameter("searchUserId");
        List<UserDTO> users = null;

        if(!ValidationUtil.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null) users = List.of();
        } else {
            users = userService.findAll();
        }

        req.setAttribute("searchUserId", searchUserId);
        req.getRequestDispatcher("/WEB-INF/jsp/userFavourite.jsp").forward(req, resp);
    }
}
