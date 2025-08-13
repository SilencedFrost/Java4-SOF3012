package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.CategoryFormFields;
import com.dto.CategoryDTO;
import com.service.CategoryService;
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

@WebServlet ("/category")
public class CategoryServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CategoryServlet.class.getName());
    private static final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ServletUtil.refreshCsrfToken(req);

        List<CategoryDTO> categories = categoryService.findAll();

        ServletUtil.constructForm(req, CategoryFormFields.class);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.DELETE, ButtonFormFields.UPDATE, ButtonFormFields.RESET);
        ServletUtil.setTableData(req, categories, CategoryFormFields.class);

        req.getRequestDispatcher("/WEB-INF/jsp/Category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String categoryId = formData.get(CategoryFormFields.CATEGORY_ID.getPropertyKey());
        String categoryName = formData.get(CategoryFormFields.CATEGORY_NAME.getPropertyKey());

        categoryService.create(categoryId, categoryName);

        JsonUtil.sendJsonRedirect(resp, "/category");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String categoryId = formData.get(CategoryFormFields.CATEGORY_ID.getPropertyKey());
        String categoryName = formData.get(CategoryFormFields.CATEGORY_NAME.getPropertyKey());

        categoryService.update(new CategoryDTO(categoryId, categoryName));

        JsonUtil.sendJsonRedirect(resp, "/category");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String categoryId = formData.get(CategoryFormFields.CATEGORY_ID.getPropertyKey());

        categoryService.delete(categoryId);

        JsonUtil.sendJsonRedirect(resp, "/category");
    }
}
