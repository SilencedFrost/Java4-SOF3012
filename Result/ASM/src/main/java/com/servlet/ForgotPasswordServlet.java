package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.dto.VideoDTO;
import com.email.EmailSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.PasswordHasher;
import com.service.UserService;
import com.service.VideoService;
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
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/forgot-password"}
)
public class ForgotPasswordServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ForgotPasswordServlet.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);

        ServletUtil.constructForm(req, UserFormFields.USER_ID, UserFormFields.EMAIL);
        ServletUtil.populateButtons(req, ButtonFormFields.SUBMIT);

        req.getRequestDispatcher("/WEB-INF/jsp/forgotPassword.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Session processing
        HttpSession session = req.getSession(false);

        Map<String, Object> respMap = new HashMap<>();

        if(session == null) {
            respMap.put("forbiddenError", "Session expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Map object initialization
        Map<String, String> reqMap = ServletUtil.readJsonAsMap(req);
        Map<String, String> errors = new HashMap<>();

        // CSRF check
        String formToken = reqMap.get("csrfToken");

        String sessionToken = (String) session.getAttribute("csrfToken");
        session.removeAttribute("csrfToken");

        if (formToken == null || !formToken.equals(sessionToken)) {
            respMap.put("forbiddenError", "Security token expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Extraction of form fields
        String userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
        String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());

        // Service initialization late down the chain to save resource
        UserService userService = new UserService();

        // Field validation
        // UserId
        if(ValidationUtil.isNullOrBlank(userId)) {
            errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id cannot be empty!");
        }

        // Email
        if(ValidationUtil.isNullOrBlank(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email cannot be empty!");
        } else if (!ValidationUtil.isValidEmail(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email is invalid!");
        }

        if(errors.isEmpty()) {
            // Check for credentials
            if(userService.findById(userId.toLowerCase().trim()).getEmail().equals(email.toLowerCase().trim())){
                // Update password to DefaultP4$$
                if(userService.update(userId, PasswordHasher.hash("DefaultP4$$$"), null, null, null)) {
                    EmailSender.sendEmail(email, "PolyOE Online Entertainment password reset", "Due to a request, your password have been reset to DefaultP4$$, please login with this password and change it as soon as possible");
                }
            }
            // Notification
            errors.put("specialError", "request sent, if your credentials are valid, you will receive an email");

            // Reload CSRF token
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", csrfToken);
            respMap.put("csrfToken", csrfToken);

            // Send response back
            respMap.put("errors", errors);

            String json = mapper.writeValueAsString(respMap);

            ServletUtil.sendJsonResp(resp, json, HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            // If failed blank field validation
            // Reload CSRF token
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", csrfToken);
            respMap.put("csrfToken", csrfToken);

            // Send response back
            respMap.put("errors", errors);

            String json = mapper.writeValueAsString(respMap);

            ServletUtil.sendJsonResp(resp, json, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
