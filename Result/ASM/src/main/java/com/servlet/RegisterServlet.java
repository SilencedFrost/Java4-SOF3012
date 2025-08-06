package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.email.EmailSender;
import com.security.PasswordHasher;
import com.service.UserService;
import com.util.ServletUtil;
import com.util.JsonUtil;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());
    private static final UserService userService = new UserService();
    private static final CustomFormFields loginLink = new CustomFormFields("/login", "already has an acount?", "link", null);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);

        ServletUtil.constructForm(req, UserFormFields.USER_ID, UserFormFields.FULL_NAME, UserFormFields.EMAIL, UserFormFields.PASSWORD, loginLink);
        ServletUtil.populateButtons(req, ButtonFormFields.REGISTER);

        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();

        Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
            String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());
            String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
            String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());

            // Field validation
            ValidationUtil.validateUserId(userId, errors);
            ValidationUtil.validateFullName(fullName, errors);
            ValidationUtil.validateEmail(email, errors);
            ValidationUtil.validatePassword(password, errors);

            if (errors.isEmpty()) {
                if (userService.create(userId, PasswordHasher.hash(password), fullName, email, "User")) {
                    EmailSender.sendEmail(email, "Thank you for registering to PolyOE Online Entertainment", "Dear " + fullName + " thank you for registering to PolyOE Online Entertainment, your user ID is" + userId + ".Please remember this so you can login later, cheers!");
                    JsonUtil.sendJsonRedirect(resp, "/login");
                } else {
                    // If failed user creation
                    errors.put("specialError", "User creation failed");
                }
            }
            // Reload CSRF token
            ServletUtil.refreshCsrfToken(req, respMap);

            // Send response back
            respMap.put("errors", errors);

            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
