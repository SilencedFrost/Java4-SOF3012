package com.util;

import com.constants.Automatable;
import com.constants.Buttons;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class ServletUtil {
    private static final Logger logger = Logger.getLogger(ServletUtil.class.getName());

    public static void constructForm(HttpServletRequest req, String apiPath, Automatable... fields) {
        req.setAttribute("apiPath", apiPath);
        req.setAttribute("fieldStructure", fields);
    }

    public static void constructForm(HttpServletRequest req, Automatable... fields) {
        constructForm(req, "", fields);
    }

    public static <T extends Enum<T> & Automatable> void constructForm(HttpServletRequest req, String apiPath, Class<T> enumClass) {
        constructForm(req, apiPath, enumClass.getEnumConstants());
    }

    public static <T extends Enum<T> & Automatable> void constructForm(HttpServletRequest req, Class<T> enumClass) {
        constructForm(req, "", enumClass.getEnumConstants());
    }

    public static <D> void setTableData(HttpServletRequest req, List<D> dataList, Automatable... fields) {
        setTableData(req, null, dataList, fields);
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, List<D> dataList, Class<T> enumClass) {
        setTableData(req, dataList, enumClass.getEnumConstants());
    }

    public static <D> void setTableData(HttpServletRequest req, Integer i, List<D> dataList, Automatable... fields) {
        String index = "";
        if(i != null && i > 0) {
            index = String.valueOf(i);
        }

        req.setAttribute("tableFields" + index, fields);
        req.setAttribute("dataList"  + index, dataList);
    }

    public static <T extends Enum<T> & Automatable, D> void setTableData(HttpServletRequest req, Integer index,  List<D> dataList, Class<T> enumClass) {
        setTableData(req, index, dataList, enumClass.getEnumConstants());
    }

    public static void populateButtons(HttpServletRequest req, Buttons... buttons) {
        req.setAttribute("buttons", buttons);
    }

    public static <T extends Enum<T> & Buttons> void populateButtons(HttpServletRequest req, Class<T> enumClass) {
        populateButtons(req, enumClass.getEnumConstants());
    }

    public static void refreshCsrfToken(HttpServletRequest req, Map<String, Object> respMap) {
        String csrfToken = UUID.randomUUID().toString();
        req.getSession().setAttribute("csrfToken", csrfToken);
        respMap.put("csrfToken", csrfToken);
    }

    public static void refreshCsrfToken(HttpServletRequest req) {
        String csrfToken = UUID.randomUUID().toString();
        req.getSession().setAttribute("csrfToken", csrfToken);
        req.setAttribute("csrfToken", csrfToken);
    }

    public static Map<String, String> basicFormCheck(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors, Map<String, Object> respMap) throws ServletException, IOException{
        // Session processing
        HttpSession session = req.getSession(false);

        if(session == null) {
            respMap.put("forbiddenError", "Session expired, reload page");
            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        // Map object initialization
        Map<String, String> reqMap = JsonUtil.readJsonAsMap(req);

        // CSRF check
        String formToken = reqMap.get("csrfToken");

        String sessionToken = (String) session.getAttribute("csrfToken");
        session.removeAttribute("csrfToken");

        if (formToken == null || !formToken.equals(sessionToken)) {
            respMap.put("forbiddenError", "Security token expired, reload page");
            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return reqMap;
    }
}
