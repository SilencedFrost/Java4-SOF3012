package com.servlet;

import com.dto.UserDTO;
import com.security.PasswordHasher;
import com.service.UserService;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/login", "/logout"})
public class AuthServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();
        if ("/logout".equals(req.getServletPath())) { session.invalidate(); resp.sendRedirect("/login"); return;}

        req.setAttribute("idOrEmailError", session.getAttribute("idOrEmailError"));
        session.removeAttribute("idOrEmailError");
        req.setAttribute("passwordError", session.getAttribute("passwordError"));
        session.removeAttribute("passwordError");
        req.setAttribute("loginError", session.getAttribute("loginError"));
        session.removeAttribute("loginError");

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userIdOrEmail = req.getParameter("idOrEmail");
        String password = req.getParameter("password");

        boolean error = false;

        if(ValidationUtil.isNullOrBlank(userIdOrEmail)) {
            session.setAttribute("idOrEmailError", "User ID or Email cannot be empty.");
            error = true;
        }
        if(ValidationUtil.isNullOrBlank(password)) {
            session.setAttribute("passwordError", "Password cannot be empty.");
            error = true;
        }

        if(!error) {
            UserDTO userDTO = new UserService().findByIdOrEmail(userIdOrEmail);
            if(userDTO != null && PasswordHasher.verify(password, userDTO.getPasswordHash())) {
                session.setAttribute("user", userDTO);
                resp.sendRedirect("user");
                return;
            }
            else {
                session.setAttribute("loginError", "User or password does not match.");
            }
        }
        resp.sendRedirect("login");
    }
}
