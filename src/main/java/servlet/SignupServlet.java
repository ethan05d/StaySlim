package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class SignupServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String email    = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password");
        double height   = Double.parseDouble(req.getParameter("height"));

        try {
            if (userService.usernameExists(username)) {
                req.setAttribute("error", "Username \"" + username + "\" is already taken.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp")
                        .forward(req, resp);
                return;
            }
            if (userService.emailExists(email)) {
                req.setAttribute("error", "That email is already registered.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp")
                        .forward(req, resp);
                return;
            }

            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPasswordHash(password); // will be hashed inside register()
            u.setHeightCm(height);

            userService.register(u);

            resp.sendRedirect(req.getContextPath() + "/login?message=Account+created!");
        }
        catch (SQLException e) {
            if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                req.setAttribute("error", "Username or email already in use.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp")
                        .forward(req, resp);
            } else {
                throw new ServletException(e);
            }
        }
    }
}
