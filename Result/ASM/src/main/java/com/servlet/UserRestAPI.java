package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.security.PasswordHasher;
import com.service.UserService;
import com.util.JsonUtil;
import com.util.ServletUtil;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/api/user/*")
public class UserRestAPI extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserRestAPI.class.getName());
    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());
        String action = path.substring("/api".length());
        logger.info(
                "Get request received \n" +
                "Uri: " + uri + "\n" +
                "contextPath: " + contextPath + "\n" +
                "Path:" + path + "\n" +
                "Action: " + action + "\n");
        String requestUrl;

        requestUrl = "/user";
        if(action.length() == requestUrl.length()) {
            JsonUtil.sendJsonResp(resp, userService.findAll(), HttpServletResponse.SC_OK);
        } else {
            UserDTO user = userService.findById(action.substring(requestUrl.length() + 1));
            if(user == null) {
                JsonUtil.sendJsonResp(resp, "Could not find " + requestUrl.substring(1), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                JsonUtil.sendJsonResp(resp, user, HttpServletResponse.SC_OK);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());
        String action = path.substring("/api".length());
        logger.info(
                "Post request received \n" +
                "Uri: " + uri + "\n" +
                "contextPath: " + contextPath + "\n" +
                "Path:" + path + "\n" +
                "Action: " + action + "\n");

            Map<String, String> errors = new HashMap<>();
            Map<String, Object> respMap = new HashMap<>();
            HttpSession session = req.getSession();

            Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
            String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());
            String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
            String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());
            String role = reqMap.get(UserFormFields.ROLE.getPropertyKey());

            // Field validation
            ValidationUtil.validateUserId(userId, errors);
            ValidationUtil.validateFullName(fullName, errors);
            ValidationUtil.validateEmail(email, errors);
            ValidationUtil.validatePassword(password, errors);
            if(role == null) role = "User";

            if (errors.isEmpty()) {
                if (userService.create(userId, PasswordHasher.hash(password), fullName, email, role)) {
                    JsonUtil.sendJsonRedirect(resp, "/admin/users");
                } else {
                    // If failed user creation
                    errors.put("specialError", "User creation failed");

                    // Reload CSRF token
                    String csrfToken = UUID.randomUUID().toString();
                    session.setAttribute("csrfToken", csrfToken);
                    respMap.put("csrfToken", csrfToken);

                    // Send response back
                    respMap.put("errors", errors);

                    JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                // If failed blank field validation
                // Reload CSRF token
                String csrfToken = UUID.randomUUID().toString();
                session.setAttribute("csrfToken", csrfToken);
                respMap.put("csrfToken", csrfToken);

                // Send response back
                respMap.put("errors", errors);

                JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
