package com.servlet;

import com.constants.ButtonFormFields;
import com.constants.BookFormFields;
import com.entity.Book;
import com.service.AuthorService;
import com.service.BookService;
import com.util.JsonUtil;
import com.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet ("/book/*")
public class BookServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BookServlet.class.getName());
    private static final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorId = null;
        if(req.getRequestURI().length() > "/book/".length()) authorId = req.getRequestURI().substring("/book/".length());

        List<Book> books = bookService.findAll();

        List<Map< String, Object>> dataList = new ArrayList<>();

        for(Book book : books) {
            if(book.getAuthor().getAuthorId().equals(authorId)) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("authorId", book.getAuthor().getAuthorId());
                dataMap.put("bookId", book.getBookId());
                dataMap.put("price", book.getPrice());
                dataMap.put("title", book.getTitle());
                dataList.add(dataMap);
            }

        }

        ServletUtil.setTableData(req, dataList, BookFormFields.class);

        req.getRequestDispatcher("/WEB-INF/jsp/Book.jsp").forward(req, resp);
    }
}
