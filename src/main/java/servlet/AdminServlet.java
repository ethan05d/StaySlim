package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private final UserService userService = new UserService();

    // show the admin page with a list of all users
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // fetch all users from the service
            List<User> all = userService.findAll();
            // put list into request scope for JSP
            req.setAttribute("allUsers", all);
            req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // handle form submissions from the admin page
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        // determine what action was requested
        String action = req.getParameter("_method");

        try {
            if ("delete".equals(action)) {
                // delete the user with given ID
                userService.delete(id);
            }

        } catch(SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath()+"/app/admin");
    }

    // allow client-side delete-all via HTTP DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // wipe all related data for all users
            userService.resetAll();
            // respond with 204 No Content on success
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch(SQLException e) {
            throw new ServletException(e);
        }
    }
}
