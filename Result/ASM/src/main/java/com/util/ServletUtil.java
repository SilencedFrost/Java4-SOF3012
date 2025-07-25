package com.util;

import com.constants.Automatable;
import com.constants.Buttons;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ServletUtil {
    private static final Logger logger = Logger.getLogger(ServletUtil.class.getName());


    public static <T extends Enum<T> & Automatable> Map<String, String> getFieldData(HttpServletRequest req, Class<T> enumClass) {
        List<String> fieldNames = Automatable.getAllPropertyKeys(enumClass);
        Map<String, String> fieldData = new HashMap<>();

        for (String fieldName : fieldNames) {
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

    public static void setFieldData(HttpServletRequest req, Map<String, String> fieldData) {
        if (fieldData == null || fieldData.isEmpty()) return;

        for (Map.Entry<String, String> entry : fieldData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null) {
                req.setAttribute(key, value);
            }
        }
    }

    public static <T extends Enum<T> & Automatable> void clearFieldData(Map<String, String> pageData, Class<T> enumClass) {
        for (T field : enumClass.getEnumConstants()) {
            pageData.put(field.getPropertyKey(), "");
        }
    }

    public static void clearFieldData(Map<String, String> pageData, List<? extends Automatable> fields) {
        for (Automatable field : fields) {
            pageData.put(field.getPropertyKey(), "");
        }
    }

    public static void constructForm(HttpServletRequest req, Automatable... fields) {
        req.setAttribute("fieldStructure", fields);
    }

    public static <T extends Enum<T> & Automatable> void constructForm(HttpServletRequest req, Class<T> enumClass) {
        constructForm(req, enumClass.getEnumConstants());
    }

    public static void setErrors(HttpServletRequest req, Map<String, String> errors) {
        if (errors == null || errors.isEmpty()) return;

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            if (!ValidationUtil.isNullOrBlank(entry.getValue())) {
                req.setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public static <D> void setTableData(HttpServletRequest req, List<D> dataList, Automatable... fields) {
        req.setAttribute("tableFields", fields);
        req.setAttribute("dataList", dataList);
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, List<D> dataList, Class<T> enumClass) {
        setTableData(req, dataList, enumClass.getEnumConstants());
    }

    public static void populateButtons(HttpServletRequest req, Buttons... buttons) {
        req.setAttribute("buttons", buttons);
    }

    public static <T extends Enum<T> & Buttons> void populateButtons(HttpServletRequest req, Class<T> enumClass) {
        populateButtons(req, enumClass.getEnumConstants());
    }
}
