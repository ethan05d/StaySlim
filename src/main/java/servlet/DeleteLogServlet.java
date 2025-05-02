package servlet;

import service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/app/logs/delete")
public class DeleteLogServlet extends HttpServlet {
    private final DailyLogService logService = new DailyLogService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Require a logged‐in user
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                int logId = Integer.parseInt(idParam);
                logService.deleteLog(logId);
            } catch (NumberFormatException | SQLException e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/app/dashboard");
    }
}
