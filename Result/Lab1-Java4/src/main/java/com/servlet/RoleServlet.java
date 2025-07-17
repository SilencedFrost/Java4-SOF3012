package com.servlet;

import com.config.ConfigLoader;
import com.entity.Role;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.service.RoleService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
    name = "RoleServlet",
    urlPatterns = {"/role"}
)
public class RoleServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RoleServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/role.jsp");

        ConfigLoader.loadDatabaseConfig();

        List<Role> roleList = new RoleService().getAll();

        req.setAttribute("roleList", roleList);

        requestDispatcher.forward(req, res);
    }
}
