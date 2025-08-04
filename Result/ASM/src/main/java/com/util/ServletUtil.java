package com.util;

import com.constants.Automatable;
import com.constants.Buttons;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ServletUtil {
    private static final Logger logger = Logger.getLogger(ServletUtil.class.getName());
    private static final ObjectMapper mapper = new ObjectMapper();

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

    public static <D> void setTableData(HttpServletRequest req, int index, List<D> dataList, Automatable... fields) {
        req.setAttribute("tableFields" + String.valueOf(index), fields);
        req.setAttribute("dataList"  + String.valueOf(index), dataList);
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, int index,  List<D> dataList, Class<T> enumClass) {
        setTableData(req, index, dataList, enumClass.getEnumConstants());
    }

    public static void populateButtons(HttpServletRequest req, Buttons... buttons) {
        req.setAttribute("buttons", buttons);
    }

    public static <T extends Enum<T> & Buttons> void populateButtons(HttpServletRequest req, Class<T> enumClass) {
        populateButtons(req, enumClass.getEnumConstants());
    }

    public static void sendJsonResp(HttpServletResponse resp, String json, int httpServletResponse) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(httpServletResponse);
        resp.getWriter().write(json);
    }

    public static void sendJsonRedirect(HttpServletResponse resp, String redirectUrl) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        String json = String.format("{\"redirect\": \"%s\"}", redirectUrl);
        resp.getWriter().write(json);
    }

    public static String readJsonBody(HttpServletRequest request) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        return json.toString();
    }

    public static Map<String, String> readJsonAsMap(HttpServletRequest request) throws IOException {
        return mapper.readValue(readJsonBody(request), new TypeReference<Map<String, String>>() {});
    }
}
