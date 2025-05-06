package servlet;

import service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteLogServlet extends HttpServlet {
    private final DailyLogService logService = new DailyLogService();

    // Handle form POST requests to delete a specific log entry
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Require a logged-in user before allowing delete
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                // parse to int and call service to delete
                int logId = Integer.parseInt(idParam);
                logService.deleteLog(logId);

            } catch (NumberFormatException | SQLException e) {
                throw new ServletException(e);
            }
        }

        // redirect back to dashboard
        resp.sendRedirect(req.getContextPath() + "/app/dashboard");
    }
}
