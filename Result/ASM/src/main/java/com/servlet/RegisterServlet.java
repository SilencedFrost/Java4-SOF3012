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
        HttpSession session = req.getSession(false);

        Map<String, Object> respMap = new HashMap<>();

        if(session == null) {
            respMap.put("forbiddenError", "Session expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Map<String, String> reqMap = ServletUtil.readJsonAsMap(req);
        Map<String, String> errors = new HashMap<>();

        String formToken = reqMap.get("csrfToken");

        String sessionToken = (String) session.getAttribute("csrfToken");
        session.removeAttribute("csrfToken");

        if (formToken == null || !formToken.equals(sessionToken)) {
            respMap.put("forbiddenError", "Security token expired, reload page");
            ServletUtil.sendJsonResp(resp, mapper.writeValueAsString(respMap), HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userId = reqMap.get(UserFormFields.USER_ID.getPropertyKey());
        String fullName = reqMap.get(UserFormFields.FULL_NAME.getPropertyKey());
        String email = reqMap.get(UserFormFields.EMAIL.getPropertyKey());
        String password = reqMap.get(UserFormFields.PASSWORD.getPropertyKey());

        /* if(!error) {
            UserDTO userDTO = new UserService().findByIdOrEmail(userIdOrEmail);
            if(userDTO != null && PasswordHasher.verify(password, userDTO.getPasswordHash())) {
                session.setAttribute("user", userDTO);
                String targetUrl = (String) session.getAttribute("targetUrl");
                session.removeAttribute("targetUrl");
                if(!ValidationUtil.isNullOrBlank(targetUrl)){
                    resp.sendRedirect(targetUrl);
                } else {
                    resp.sendRedirect("/home");
                }
                return;
            }
            else {
                session.setAttribute("loginError", "User or password does not match.");
            }
        } */
        resp.sendRedirect("/register");
    }
}
