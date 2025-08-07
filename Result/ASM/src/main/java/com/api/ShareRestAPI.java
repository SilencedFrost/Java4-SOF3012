package com.api;

import com.constants.ShareFormFields;
import com.dto.UserDTO;
import com.email.EmailSender;
import com.service.ShareService;
import com.service.VideoService;
import com.util.JsonUtil;
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
import java.util.logging.Logger;

@WebServlet("/api/share/*")
public class ShareRestAPI extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ShareRestAPI.class.getName());
    private static final ShareService shareService = new ShareService();
    private static final String requestUrl = "/share";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        logger.info("Post request received");

        Map<String, String> errors = new HashMap<>();
        Map<String, Object> respMap = new HashMap<>();

        // Session processing
        HttpSession session = req.getSession();

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if(userDTO == null) {
            JsonUtil.sendJsonRedirect(resp, "/login");
            return;
        }

        Map<String, String> reqMap = JsonUtil.readJsonAsMap(req);

        if(reqMap != null) {
            String userId = userDTO.getUserId();
            String videoId = reqMap.get(ShareFormFields.Video_ID.getPropertyKey());
            String receiverEmail = reqMap.get(ShareFormFields.RECEIVER_EMAIL.getPropertyKey());

            // Field validation

            if(ValidationUtil.isNullOrBlank(videoId)) {
                errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Video Id cannot be empty!");
            } else if (new VideoService().findById(videoId) == null) {
                errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Invalid video ID!");
            }

            if(ValidationUtil.isNullOrBlank(receiverEmail)) {
                errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Receiver email cannot be empty!");
            } else if (!ValidationUtil.isValidEmail(receiverEmail)) {
                errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Invalid email!");
            }

            if (errors.isEmpty()) {
                if(shareService.create(userId, videoId, receiverEmail)) {
                    errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Video shared to " + receiverEmail);
                    EmailSender.sendEmail(receiverEmail, "PolyOE Online Entertainment share", userDTO.getFullName() + " shared this video with you, check it out here: http://silencedfrost.freeddns.org:25595/video/watch?id=" + videoId);
                } else {
                    errors.put(ShareFormFields.RECEIVER_EMAIL.getErrorKey(), "Share failed");
                }
            }
            // Send response back
            respMap.put("errors", errors);

            JsonUtil.sendJsonResp(resp, respMap, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected String getAction(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length() + "/api".length());
    }
}
