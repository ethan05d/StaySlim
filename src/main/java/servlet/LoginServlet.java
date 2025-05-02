package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // simply render the login page
        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email    = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password");

        try {
            User user = userService.login(email, password);
            if (user == null) {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                        .forward(req, resp);
                return;
            }

            // ——— HERE: store the user in session ———
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // redirect, so the next page sees sessionScope.user populated
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        }
        catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
