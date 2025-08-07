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
    private static final String requestUrl = "/user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        logger.info("Get request received");

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
        String action = getAction(req);
        logger.info("Post request received");

        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();

        Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String userId;
            if (action.length() != requestUrl.length()) {
                if(!action.startsWith(requestUrl + "/")) {
                    JsonUtil.sendJsonResp(resp, new HashMap<>(Map.of("forbiddenError", "Malformed url")), HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                userId = action.substring(requestUrl.length() + 1);
            } else {
                userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
            }
            String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());
            String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
            String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());
            String role = reqMap.get(UserFormFields.ROLE.getPropertyKey());

            // Field validation
            ValidationUtil.validateUserId(userId, errors);
            ValidationUtil.validateFullName(fullName, errors);
            ValidationUtil.validateEmail(email, errors);
            ValidationUtil.validatePassword(password, errors);
            if(ValidationUtil.isNullOrBlank(role)) role = "User";

            if (errors.isEmpty()) {
                if (userService.create(userId, PasswordHasher.hash(password), fullName, email, role)) {
                    JsonUtil.sendJsonRedirect(resp, "");
                    return;
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        logger.info("Put request received");

        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();

        Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String userId;
            if (action.length() != requestUrl.length()) {
                if(!action.startsWith(requestUrl + "/")) {
                    JsonUtil.sendJsonResp(resp, new HashMap<>(Map.of("forbiddenError", "Malformed url")), HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                userId = action.substring(requestUrl.length() + 1);
            } else {
                userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
            }
            String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());
            String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
            String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());
            String role = reqMap.get(UserFormFields.ROLE.getPropertyKey());

            // Field validation
            // User Id
            if(ValidationUtil.isNullOrBlank(userId)) {
                errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id cannot be empty!");
            } else if (userService.findById(userId) == null) {
                errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id does not exist");
            }

            // Full name
            if(ValidationUtil.isNullOrBlank(fullName)) fullName = null;

            // Email
            if(ValidationUtil.isNullOrBlank(email)) {
                email = null;
            } else if (!ValidationUtil.isValidEmail(email)) {
                errors.put(UserFormFields.EMAIL.getErrorKey(), "Email is invalid!");
            } else if (userService.findByEmail(email) != null) {
                errors.put(UserFormFields.EMAIL.getErrorKey(), "Email already taken");
            }

            // Password
            if(ValidationUtil.isNullOrBlank(password)){
                password = null;
            } else if (!ValidationUtil.isValidPassword(password)) {
                errors.put(UserFormFields.PASSWORD.getErrorKey(), "8-32 characters with uppercase, lowercase, number & special character");
            }

            // Role
            if(ValidationUtil.isNullOrBlank(role)) role = "User";

            if (errors.isEmpty()) {
                if (userService.update(userId, PasswordHasher.hash(password), fullName, email, role)) {
                    JsonUtil.sendJsonRedirect(resp, "");
                    return;
                } else {
                    // If failed user creation
                    errors.put("specialError", "User update failed");
                }
            }
            // Reload CSRF token
            ServletUtil.refreshCsrfToken(req, respMap);

            // Send response back
            respMap.put("errors", errors);

            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        logger.info("Delete request received");

        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();

        Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String userId;
            if (action.length() != requestUrl.length()) {
                if(!action.startsWith(requestUrl + "/")) {
                    JsonUtil.sendJsonResp(resp, new HashMap<>(Map.of("forbiddenError", "Malformed url")), HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                userId = action.substring(requestUrl.length() + 1);
            } else {
                userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
            }

            // Field validation
            // User Id
            if(ValidationUtil.isNullOrBlank(userId)) {
                errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id cannot be empty!");
            } else if (userService.findById(userId) == null) {
                errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id does not exist");
            }

            if (errors.isEmpty()) {
                if (userService.delete(userId)) {
                    JsonUtil.sendJsonRedirect(resp, "");
                    return;
                } else {
                    // If failed user deletion
                    errors.put("specialError", "User deletion failed");
                }
            }
            // Reload CSRF token
            ServletUtil.refreshCsrfToken(req, respMap);

            // Send response back
            respMap.put("errors", errors);

            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected String getAction(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length() + "/api".length());
    }
}
