package com.servlet;

import com.config.ConfigLoader;
import com.dto.UserDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
    name = "UserServlet",
    urlPatterns = {"/user"}
)
public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService(); // ideally injected or loaded
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("tableHeaders", UserDTO.getHeaders());
        req.setAttribute("tableData", UserDTO.toListOfLists(userService.getAll()));
        req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
    }
}
