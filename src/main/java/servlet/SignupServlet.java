package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class SignupServlet extends HttpServlet {
    private final UserService userService = new UserService();

    // Show the signup page on GET requests
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
    }

    // Process signup form submissions (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // read form inputs
        String username = req.getParameter("username").trim();
        String email = req.getParameter("email").trim().toLowerCase();
        String password = req.getParameter("password");
        double height = Double.parseDouble(req.getParameter("height"));

        try {
            // check if username is already taken
            if (userService.usernameExists(username)) {
                req.setAttribute("error", "Username \"" + username + "\" is already taken.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
                return;
            }
            // check if email is already registered
            if (userService.emailExists(email)) {
                req.setAttribute("error", "That email is already registered.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
                return;
            }

            // build new User object
            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPasswordHash(password); // password will be hashed in service
            u.setHeightCm(height);

            // register user in database
            userService.register(u);

            resp.sendRedirect(req.getContextPath() + "/login?message=Account+created!");
        }
        catch (SQLException e) {
            if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                req.setAttribute("error", "Username or email already in use.");
                req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            } else {
                throw new ServletException(e);
            }
        }
    }
}
