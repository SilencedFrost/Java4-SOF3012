package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.VideoFormFields;
import com.dto.VideoDTO;
import com.service.VideoService;
import com.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
        urlPatterns = {"/admin/videos"}
)
public class adminVideoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(adminVideoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoDTO> videoList = new VideoService().findAll();

        ServletUtil.setTableData(req, videoList, VideoFormFields.VIDEO_ID, VideoFormFields.TITLE, VideoFormFields.VIEWS, VideoFormFields.ACTIVE);
        ServletUtil.constructForm(req, VideoFormFields.VIDEO_ID, VideoFormFields.TITLE, VideoFormFields.VIEWS, VideoFormFields.ACTIVE, VideoFormFields.DESCRIPTION);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.UPDATE, ButtonFormFields.DELETE, ButtonFormFields.RESET);
        req.getRequestDispatcher("/WEB-INF/jsp/adminVideo.jsp").forward(req, resp);
    }
}
