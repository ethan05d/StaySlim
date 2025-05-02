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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<User> all = userService.findAll();
            req.setAttribute("allUsers", all);
            req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("_method");
        try {
            if ("delete".equals(action)) {
                userService.delete(id);
            }
        } catch(SQLException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath()+"/app/admin");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            userService.resetAll();
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch(SQLException e) {
            throw new ServletException(e);
        }
    }
}