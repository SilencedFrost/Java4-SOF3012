package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.util.ServletUtil;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet ("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AdminUserServlet.class.getName());
    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String targetUrl = (String) session.getAttribute("targetUrl");
        session.removeAttribute("targetUrl");
        req.setAttribute("targetUrl", targetUrl);

        ServletUtil.refreshCsrfToken(req);

        String searchUserId = req.getParameter("search");
        List<UserDTO> users;

        if(!ValidationUtil.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null) users = List.of();
        } else {
            users = userService.findAll();
        }

        ServletUtil.constructForm(req, "/api/user", UserFormFields.class);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.DELETE, ButtonFormFields.UPDATE, ButtonFormFields.RESET);
        ServletUtil.setTableData(req, users, UserFormFields.USER_ID, UserFormFields.EMAIL, UserFormFields.FULL_NAME, UserFormFields.ROLE);

        req.getRequestDispatcher("/WEB-INF/jsp/adminUser.jsp").forward(req, resp);
    }
}
