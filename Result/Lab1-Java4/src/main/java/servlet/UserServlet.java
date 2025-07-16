package servlet;

import config.ConfigLoader;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet (
    name = "UserServlet",
    urlPatterns = {"/user"}
)
public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");

        ConfigLoader.loadDatabaseConfig();

        UserService userService = new UserService();
        List<User> userList = userService.getAll();

        req.setAttribute("userList", userList);
        requestDispatcher.forward(req, res);
    }
}
