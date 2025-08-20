package com.api;

import com.constants.AuthorFormFields;
import com.entity.Author;
import com.service.AuthorService;
import com.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/api/author/*")
public class AuthorAPI extends HttpServlet {
    Logger logger = Logger.getLogger(AuthorAPI.class.getName());
    private static final AuthorService authorService = new AuthorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorId = req.getRequestURI().substring("/api/author/".length());

        logger.info(authorId);

        Author author = authorService.findById(authorId);

        JsonUtil.sendJsonResp(resp, Map.of("authorId", author.getAuthorId(), "authorName", author.getAuthorName()), HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String categoryId = formData.get(AuthorFormFields.AUTHOR_ID.getPropertyKey());
        String categoryName = formData.get(AuthorFormFields.AUTHOR_NAME.getPropertyKey());

        authorService.create(categoryId, categoryName);

        JsonUtil.sendJsonRedirect(resp, "/category");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String authorId = formData.get(AuthorFormFields.AUTHOR_ID.getPropertyKey());

        authorService.delete(authorId);

        JsonUtil.sendJsonRedirect(resp, "/category");
    }
}
