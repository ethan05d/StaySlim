package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    // Show the login page when a GET request is made
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    // Process login form submissions (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // read and normalize input fields
        String email = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password");

        try {
            // try to authenticate by username or email
            User user = userService.login(email, password);
            if (user == null) {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            // successful login, so create or reuse session
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        }

        catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
