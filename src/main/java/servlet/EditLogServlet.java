package servlet;

import model.DailyLog;
import model.User;
import service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditLogServlet extends HttpServlet {
    private final DailyLogService logService = new DailyLogService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        try {
            int id = Integer.parseInt(idParam);
            DailyLog log = logService.findById(id);
            if (log == null) {
                resp.sendRedirect(req.getContextPath() + "/app/dashboard");
                return;
            }
            req.setAttribute("log", log);
            req.getRequestDispatcher("/WEB-INF/views/editLog.jsp").forward(req, resp);
        } catch (NumberFormatException|SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            double weightKg = Double.parseDouble(req.getParameter("weightKg"));
            int calories = Integer.parseInt(req.getParameter("caloriesIntake"));

            DailyLog log = new DailyLog();
            log.setLogId(id);
            log.setUserId(user.getUserId()); // must set FK on update
            log.setLogDate(date);
            log.setWeightKg(weightKg);
            log.setCaloriesIntake(calories);

            logService.addOrUpdateLog(log);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
