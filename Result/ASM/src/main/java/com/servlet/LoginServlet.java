package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CustomFormFields userIdOrEmail = new CustomFormFields("userIdOrEmail", "User ID or Email", "text", null);
    private static final CustomFormFields forgotPasswordLink = new CustomFormFields("/forgot-password", "forgot password?", "link", null);
    private static final CustomFormFields registerLink = new CustomFormFields("/register", "don't have an account?", "link", null);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();

        String targetUrl = (String) session.getAttribute("targetUrl");
        session.removeAttribute("targetUrl");
        req.setAttribute("targetUrl", targetUrl);

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);

        ServletUtil.constructForm(req, userIdOrEmail, UserFormFields.PASSWORD, forgotPasswordLink, registerLink);
        ServletUtil.populateButtons(req, ButtonFormFields.LOGIN);

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();
        HttpSession session = req.getSession();

        Map<String, String> reqMap = ServletUtil.basicFormCheck(req, resp, errors, respMap);

        if(reqMap != null) {
            String idOrEmail = reqMap.get(userIdOrEmail.getPropertyKey());
            String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());
            String targetUrl = reqMap.get("targetUrl");

            // Blank field validation
            if(ValidationUtil.isNullOrBlank(idOrEmail)) errors.put(userIdOrEmail.getErrorKey(), "User ID or Email cannot be empty.");
            if(ValidationUtil.isNullOrBlank(password)) errors.put(UserFormFields.PASSWORD.getErrorKey(), "Password cannot be empty.");

            // Check if user exists and password matches
            if(errors.isEmpty()) {
                UserDTO userDTO = new UserService().findByIdOrEmail(idOrEmail);
                if(userDTO != null && PasswordHasher.verify(password, userDTO.getPasswordHash())) {
                    session.setAttribute("user", userDTO);

                    if(!ValidationUtil.isNullOrBlank(targetUrl)){
                        ServletUtil.sendJsonRedirect(resp, targetUrl);
                    } else {
                        ServletUtil.sendJsonRedirect(resp, "/home");
                    }
                    return;
                }
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
            // If user or password does not match
            errors.put("specialError", "User or password does not match.");

            // Reload CSRF token
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", csrfToken);
            respMap.put("csrfToken", csrfToken);

            // Send response back
            respMap.put("errors", errors);

            String json = mapper.writeValueAsString(respMap);

            ServletUtil.sendJsonResp(resp, json, HttpServletResponse.SC_UNAUTHORIZED);
        }


    }
}
