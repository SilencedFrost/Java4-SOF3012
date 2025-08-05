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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();

        String targetUrl = (String) session.getAttribute("targetUrl");
        session.removeAttribute("targetUrl");
        req.setAttribute("targetUrl", targetUrl);

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);

        ServletUtil.constructForm(req, userIdOrEmail, UserFormFields.PASSWORD);
        ServletUtil.populateButtons(req, ButtonFormFields.LOGIN);

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        Map<String, Object> respMap = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        if(session == null) {
            respMap.put("forbiddenError", "Session expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Map<String, String> reqMap = ServletUtil.readJsonAsMap(req);
        logger.info(reqMap.toString());

        String formToken = reqMap.get("csrfToken");

        String sessionToken = (String) session.getAttribute("csrfToken");
        session.removeAttribute("csrfToken");

        if (formToken == null || !formToken.equals(sessionToken)) {
            respMap.put("forbiddenError", "Security token expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userIdOrEmail = reqMap.get("idOrEmail");
        String password = reqMap.get("password");
        String targetUrl = reqMap.get("targetUrl");

        if(ValidationUtil.isNullOrBlank(userIdOrEmail)) errors.put("idOrEmailError", "User ID or Email cannot be empty.");

        if(ValidationUtil.isNullOrBlank(password)) errors.put("passwordError", "Password cannot be empty.");

        if(errors.isEmpty()) {
            UserDTO userDTO = new UserService().findByIdOrEmail(userIdOrEmail);
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
            String csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", csrfToken);
            respMap.put("csrfToken", csrfToken);

            respMap.put("errors", errors);

            String json = mapper.writeValueAsString(respMap);

            ServletUtil.sendJsonResp(resp, json, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        errors.put("specialError", "User or password does not match.");

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        respMap.put("csrfToken", csrfToken);

        respMap.put("errors", errors);

        String json = mapper.writeValueAsString(respMap);

        ServletUtil.sendJsonResp(resp, json, HttpServletResponse.SC_UNAUTHORIZED);
    }
}
