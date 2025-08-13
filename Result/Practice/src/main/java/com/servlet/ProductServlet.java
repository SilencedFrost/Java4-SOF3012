package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.ProductFormFields;
import com.dto.InboundProductDTO;
import com.dto.ProductDTO;
import com.dto.UpdateProductDTO;
import com.service.ProductService;
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

@WebServlet ("/product")
public class ProductServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ProductServlet.class.getName());
    private static final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ServletUtil.refreshCsrfToken(req);

        List<ProductDTO> products = productService.findAll();

        ServletUtil.constructForm(req, ProductFormFields.class);
        ServletUtil.populateButtons(req, ButtonFormFields.CREATE, ButtonFormFields.DELETE, ButtonFormFields.UPDATE, ButtonFormFields.RESET);
        ServletUtil.setTableData(req, products, ProductFormFields.class);

        req.getRequestDispatcher("/WEB-INF/jsp/Product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        String productName = formData.get(ProductFormFields.PRODUCT_NAME.getPropertyKey());
        Integer price = Integer.parseInt(formData.get(ProductFormFields.PRICE.getPropertyKey()));
        String categoryId = formData.get(ProductFormFields.CATEGORY_ID.getPropertyKey());

        productService.create(new InboundProductDTO(productName, price, categoryId));

        JsonUtil.sendJsonRedirect(resp, "/product");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        Integer productId = Integer.parseInt(formData.get(ProductFormFields.PRODUCT_ID.getPropertyKey()));
        String productName = formData.get(ProductFormFields.PRODUCT_NAME.getPropertyKey());
        Integer price = Integer.parseInt(formData.get(ProductFormFields.PRICE.getPropertyKey()));
        String categoryId = formData.get(ProductFormFields.CATEGORY_ID.getPropertyKey());

        productService.update(new UpdateProductDTO(productId, productName, price, categoryId));

        JsonUtil.sendJsonRedirect(resp, "/product");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> formData = JsonUtil.readJsonAsMap(req);

        Integer productId = Integer.parseInt(formData.get(ProductFormFields.PRODUCT_ID.getPropertyKey()));

        productService.delete(productId);

        JsonUtil.sendJsonRedirect(resp, "/product");
    }
}
