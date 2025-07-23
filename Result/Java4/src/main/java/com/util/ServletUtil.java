package com.util;

import com.constants.Automatable;
import com.constants.UserFormFields;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
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

    public static <T extends Enum<T> & Automatable> void constructFormStructure(HttpServletRequest req, Class<T> enumClass, T... fields) {
        T[] fieldsToUse = (fields == null || fields.length == 0) ? enumClass.getEnumConstants() : fields;
        req.setAttribute("fieldStructure", fieldsToUse);
    }

    public static <T extends Enum<T> & Automatable> void constructFormStructure(HttpServletRequest req, Class<T> enumClass) {
        constructFormStructure(req, enumClass, (T[]) null);
    }

    public static void setErrors(HttpServletRequest req, Map<String, String> errors) {
        if (errors == null || errors.isEmpty()) return;

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            if (!ValidationUtil.isNullOrBlank(entry.getValue())) {
                req.setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, List<D> dtoList, Class<T> enumClass, T... fields) {
        T[] fieldsToUse = (fields == null || fields.length == 0) ? enumClass.getEnumConstants() : fields;

        req.setAttribute("tableFields", fieldsToUse);
        req.setAttribute("dataList", dtoList);
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, List<D> dtoList, Class<T> enumClass) {
        setTableData(req, dtoList, enumClass, null);
    }
}
