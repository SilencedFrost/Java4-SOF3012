package com.servlet;

import com.constants.UserFormFields;
import com.dto.UserDTO;
import com.security.PasswordHasher;
import com.util.ValidationUtils;
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

        if(!ValidationUtils.isNullOrBlank(searchUserId)) {
            users = userService.findByIdLike(searchUserId);
            if(users == null)
            {
                users = List.of();
            }
        } else {
            users = userService.findAll();
        }

        setErrors(req, errors);
        setFieldData(req, pageData);
        req.setAttribute("searchUserId", searchUserId);
        setTableData(req, users);
        req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> pageData = getFieldData(req);
        Map<String, String> errors = new HashMap<>();
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        logger.info(pageData.get(UserFormFields.ROLE.propertyKey()));

        if("create".equals(action)){
            logger.info("Create action triggered");
            validateFormFields(pageData, errors);
            logger.info("Data validation completed");

            if(errors.isEmpty()) {
                userService.create(
                        pageData.get(UserFormFields.USER_ID.propertyKey()),
                        PasswordHasher.hash(pageData.get(UserFormFields.PASSWORD.propertyKey())),
                        pageData.get(UserFormFields.FULL_NAME.propertyKey()),
                        pageData.get(UserFormFields.EMAIL.propertyKey()),
                        pageData.get(UserFormFields.ROLE.propertyKey())
                );
                clearFieldData(pageData);
            }

        } else if("delete".equals(action)) {
            logger.info("Delete action triggered");
            validateFormFields(pageData, errors, List.of(UserFormFields.USER_ID));
            logger.info("Data validation completed");

            if(errors.isEmpty()) userService.delete(pageData.get(UserFormFields.USER_ID.propertyKey()));
            logger.info(pageData.get(UserFormFields.USER_ID.propertyKey()));

        } else if("update".equals(action)) {
            logger.info("Update action triggered");
            validateFormFields(pageData, errors, List.of(UserFormFields.USER_ID));
            if(!ValidationUtils.isNullOrBlank(pageData.get(UserFormFields.PASSWORD.propertyKey()))){
                validateFormFields(pageData, errors, List.of(UserFormFields.PASSWORD));
            } else {
                pageData.put(UserFormFields.PASSWORD.propertyKey(), null);
            }

            if(ValidationUtils.isNullOrBlank(pageData.get(UserFormFields.FULL_NAME.propertyKey()))) pageData.put(UserFormFields.FULL_NAME.propertyKey(), null);

            if(!ValidationUtils.isNullOrBlank(pageData.get(UserFormFields.EMAIL.propertyKey()))){
                validateFormFields(pageData, errors, List.of(UserFormFields.EMAIL));
            } else {
                pageData.put(UserFormFields.EMAIL.propertyKey(), null);
            }
            logger.info("Data validation completed");

            if(errors.isEmpty()) {
                userService.update(
                        pageData.get(UserFormFields.USER_ID.propertyKey()),
                        PasswordHasher.hash(pageData.get(UserFormFields.PASSWORD.propertyKey())),
                        pageData.get(UserFormFields.FULL_NAME.propertyKey()),
                        pageData.get(UserFormFields.EMAIL.propertyKey()),
                        pageData.get(UserFormFields.ROLE.propertyKey()));
                clearFieldData(pageData);
            }

        } else if("reset".equals((action))) {
            clearFieldData(pageData);
        }

        session.setAttribute("errors", errors);
        session.setAttribute("pageData", pageData);
        resp.sendRedirect("/user");
    }

    private Map<String, String> getFieldData(HttpServletRequest req) {
        List<String> fieldNames = UserFormFields.getAllPropertyKeys();
        Map<String, String> fieldData = new HashMap<>();
        for(String fieldName : fieldNames) {
            String value = req.getParameter(fieldName);
            if (value != null) {
                fieldData.put(fieldName, value);
            } else {
                logger.warning("Field " + fieldName + " is null.");
                fieldData.put(fieldName, null);
            }
        }
        return fieldData;
    }

    private void setFieldData(HttpServletRequest req, Map<String, String> fieldData) {
        if(fieldData != null) {
            for (Map.Entry<String, String> entry : fieldData.entrySet()) {
                if(entry.getValue() != null) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void setTableData(HttpServletRequest req, List<UserDTO> users, UserFormFields... fields) {
        UserFormFields[] fieldsToUse = (fields.length == 0) ? UserFormFields.values() : fields;

        req.setAttribute("userFields", fieldsToUse);
        req.setAttribute("users", users);
    }

    private void setTableData(HttpServletRequest req) {
        setTableData(req, userService.findAll());
    }

    private void setTableData(HttpServletRequest req, List<UserDTO> users) {
        setTableData(req, users, new UserFormFields[0]); // Empty array = use all fields
    }

    private void setTableData(HttpServletRequest req, UserFormFields... fields) {
        setTableData(req, userService.findAll(), fields);
    }

    private void setErrors(HttpServletRequest req, Map<String, String> errors) {
        if(errors != null) {
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                if(!ValidationUtils.isNullOrBlank(entry.getValue())) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void validateFormFields(Map<String, String> pageData, Map<String, String> errors) {
        validateFormFields(pageData, errors, List.of(UserFormFields.values()));
    }

    private void validateFormFields(Map<String, String> pageData, Map<String, String> errors, List<UserFormFields> fieldsToValidate) {
        for (UserFormFields field : fieldsToValidate) {
            String value = pageData.get(field.propertyKey());

            if (field.getRequiredError() != null && ValidationUtils.isNullOrBlank(value)) {
                errors.put(field.errorKey(), field.getRequiredError().getMessage());
                continue;
            }

            if (field.getFormatError() != null) {
                boolean formatInvalid = switch (field) {
                    case PASSWORD -> !ValidationUtils.isValidPassword(value);
                    case EMAIL -> !ValidationUtils.isValidEmail(value);
                    default -> false;
                };

                if (formatInvalid) {
                    errors.put(field.errorKey(), field.getFormatError().getMessage());
                }
            }
        }
    }

    private void clearFieldData(Map<String, String> pageData) {
        clearFieldData(pageData, List.of(UserFormFields.values()));
    }
    private void clearFieldData(Map<String, String> pageData, List<UserFormFields> fields) {
        for(UserFormFields field : fields) {
            pageData.put(field.propertyKey(), "");
        }
    }
}
