package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
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
import java.util.logging.Logger;

@WebServlet (
    urlPatterns = {"/user"}
)
public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchUserId = req.getParameter("searchUserId");
        HttpSession session = req.getSession();
        List<UserDTO> users = null;

        // Session data extraction
        Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
        session.removeAttribute("errors");
        Map<String, String> pageData = (Map<String, String>) session.getAttribute("pageData");
        session.removeAttribute("pageData");

        if(!ValidationUtil.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null)
            {
                users = List.of();
            }
        } else {
            users = userService.findAll();
        }

        ServletUtil.setErrors(req, errors);
        ServletUtil.setFieldData(req, pageData);
        req.setAttribute("searchUserId", searchUserId);
        ServletUtil.constructFormStructure(req,UserFormFields.class);
        ServletUtil.setTableData(req, users, UserFormFields.class);
        // setTableData(req, users);
        req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> pageData = ServletUtil.getFieldData(req, UserFormFields.class);
        Map<String, String> errors = new HashMap<>();
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        logger.info(pageData.get(UserFormFields.ROLE.getPropertyKey()));

        if("create".equals(action)){
            logger.info("Create action triggered");
            validateFormFields(pageData, errors);
            logger.info("Data validation completed");

            if(errors.isEmpty()) {
                userService.create(
                        pageData.get(UserFormFields.USER_ID.getPropertyKey()),
                        PasswordHasher.hash(pageData.get(UserFormFields.PASSWORD.getPropertyKey())),
                        pageData.get(UserFormFields.FULL_NAME.getPropertyKey()),
                        pageData.get(UserFormFields.EMAIL.getPropertyKey()),
                        pageData.get(UserFormFields.ROLE.getPropertyKey())
                );
                ServletUtil.clearFieldData(pageData, UserFormFields.class);
            }

        } else if("delete".equals(action)) {
            logger.info("Delete action triggered");
            validateFormFields(pageData, errors, List.of(UserFormFields.USER_ID));
            logger.info("Data validation completed");

            if(errors.isEmpty()) userService.delete(pageData.get(UserFormFields.USER_ID.getPropertyKey()));
            logger.info(pageData.get(UserFormFields.USER_ID.getPropertyKey()));

        } else if("update".equals(action)) {
            logger.info("Update action triggered");
            validateFormFields(pageData, errors, List.of(UserFormFields.USER_ID));
            if(!ValidationUtil.isNullOrBlank(pageData.get(UserFormFields.PASSWORD.getPropertyKey()))){
                validateFormFields(pageData, errors, List.of(UserFormFields.PASSWORD));
            } else {
                pageData.put(UserFormFields.PASSWORD.getPropertyKey(), null);
            }

            if(ValidationUtil.isNullOrBlank(pageData.get(UserFormFields.FULL_NAME.getPropertyKey()))) pageData.put(UserFormFields.FULL_NAME.getPropertyKey(), null);

            if(!ValidationUtil.isNullOrBlank(pageData.get(UserFormFields.EMAIL.getPropertyKey()))){
                validateFormFields(pageData, errors, List.of(UserFormFields.EMAIL));
            } else {
                pageData.put(UserFormFields.EMAIL.getPropertyKey(), null);
            }
            logger.info("Data validation completed");

            if(errors.isEmpty()) {
                userService.update(
                        pageData.get(UserFormFields.USER_ID.getPropertyKey()),
                        PasswordHasher.hash(pageData.get(UserFormFields.PASSWORD.getPropertyKey())),
                        pageData.get(UserFormFields.FULL_NAME.getPropertyKey()),
                        pageData.get(UserFormFields.EMAIL.getPropertyKey()),
                        pageData.get(UserFormFields.ROLE.getPropertyKey()));
                ServletUtil.clearFieldData(pageData, UserFormFields.class);
            }

        } else if("reset".equals((action))) {
            ServletUtil.clearFieldData(pageData, UserFormFields.class);
        }

        session.setAttribute("errors", errors);
        session.setAttribute("pageData", pageData);
        resp.sendRedirect("/user");
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
