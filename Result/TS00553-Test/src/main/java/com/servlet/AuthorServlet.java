package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.AuthorFormFields;
import com.entity.Author;
import com.service.AuthorService;
import com.util.JsonUtil;
import com.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet ({"/author" , "/"})
public class AuthorServlet extends HttpServlet {
    private static final AuthorService authorService = new AuthorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletUtil.refreshCsrfToken(req);

        List<Author> categories = authorService.findAll();

        ServletUtil.constructForm(req, "/api/author", AuthorFormFields.class);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.DELETE);
        ServletUtil.setTableData(req, categories, AuthorFormFields.class);

        req.getRequestDispatcher("/WEB-INF/jsp/Author.jsp").forward(req, resp);
    }
}
