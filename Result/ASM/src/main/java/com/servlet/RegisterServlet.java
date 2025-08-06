package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CustomFormFields;
import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.email.EmailSender;
import com.entity.User;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();
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
        String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
        String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());
        String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());

        // Service initialization late down the chain to save resource
        UserService userService = new UserService();

        // Field validation
        // UserId
        if(ValidationUtil.isNullOrBlank(userId)) {
            errors.put(UserFormFields.USER_ID.getErrorKey(), "User Id cannot be empty!");
        } else if (
                userService.findById(userId) != null ||
                userService.findByEmail(userId) != null ||
                ValidationUtil.isValidEmail(userId)) {
            errors.put(UserFormFields.USER_ID.getErrorKey(), "Invalid user ID");
        }

        // Full name
        if(ValidationUtil.isNullOrBlank(fullName)) {
            errors.put(UserFormFields.FULL_NAME.getErrorKey(), "Full name cannot be empty!");
        }

        // Email
        if(ValidationUtil.isNullOrBlank(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email cannot be empty!");
        } else if (!ValidationUtil.isValidEmail(email)) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email is invalid!");
        } else if (userService.findByEmail(email) != null) {
            errors.put(UserFormFields.EMAIL.getErrorKey(), "Email already taken");
        }

        // Password
        if(ValidationUtil.isNullOrBlank(password)){
            errors.put(UserFormFields.PASSWORD.getErrorKey(), "Password cannot be empty!");
        } else if (!ValidationUtil.isValidPassword(password)) {
            errors.put(UserFormFields.PASSWORD.getErrorKey(), "8-32 characters with uppercase, lowercase, number & special character");
        }

        if(errors.isEmpty()) {
            if(userService.create(userId, PasswordHasher.hash(password), fullName, email, "User")) {
                EmailSender.sendEmail(email, "Thank you for registering to PolyOE Online Entertainment", "Dear " + fullName + " thank you for registering to PolyOE Online Entertainment, your user ID is" + userId + ".Please remember this so you can login later, cheers!");
                ServletUtil.sendJsonRedirect(resp, "/login");
            } else {
                // If failed user creation
                errors.put("specialError", "User creation failed");

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
