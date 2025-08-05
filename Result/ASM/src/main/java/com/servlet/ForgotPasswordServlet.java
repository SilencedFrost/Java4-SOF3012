package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.dto.VideoDTO;
import com.service.VideoService;
import com.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/forgot-password"}
)
public class ForgotPasswordServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ForgotPasswordServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletUtil.constructForm(req, UserFormFields.USER_ID, UserFormFields.EMAIL);
        ServletUtil.populateButtons(req, ButtonFormFields.SUBMIT);

        req.getRequestDispatcher("/WEB-INF/jsp/forgotPassword.jsp").forward(req, resp);
    }
}
