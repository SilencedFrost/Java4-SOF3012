package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.PasswordHasher;
import com.util.ServletUtil;
import com.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet ("/admin/users")
public class adminUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(adminUserServlet.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String targetUrl = (String) session.getAttribute("targetUrl");
        session.removeAttribute("targetUrl");
        req.setAttribute("targetUrl", targetUrl);

        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);

        String searchUserId = req.getParameter("search");
        List<UserDTO> users;

        if(!ValidationUtil.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null) users = List.of();
        } else {
            users = userService.findAll();
        }

        ServletUtil.constructForm(req,UserFormFields.class);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.DELETE, ButtonFormFields.UPDATE, ButtonFormFields.RESET);
        ServletUtil.setTableData(req, users, UserFormFields.class);
        req.getRequestDispatcher("/WEB-INF/jsp/adminUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void validateFormFields(Map<String, String> pageData, Map<String, String> errors) {
        validateFormFields(pageData, errors, List.of(UserFormFields.values()));
    }

    private void validateFormFields(Map<String, String> pageData, Map<String, String> errors, List<UserFormFields> fieldsToValidate) {
        for (UserFormFields field : fieldsToValidate) {
            String value = pageData.get(field.getPropertyKey());

            if (field.getRequiredError() != null && ValidationUtil.isNullOrBlank(value)) {
                errors.put(field.getErrorKey(), field.getRequiredError().getMessage());
                continue;
            }

            if (field.getFormatError() != null) {
                boolean formatInvalid = switch (field) {
                    case PASSWORD -> !ValidationUtil.isValidPassword(value);
                    case EMAIL -> !ValidationUtil.isValidEmail(value);
                    default -> false;
                };

                if (formatInvalid) {
                    errors.put(field.getErrorKey(), field.getFormatError().getMessage());
                }
            }
        }
    }
}
