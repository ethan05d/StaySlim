package servlet;

import model.DailyLog;
import model.User;
import service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class DailyLogServlet extends HttpServlet {
    private final DailyLogService logService = new DailyLogService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");

        String dateStr = req.getParameter("date");
        String weightStr = req.getParameter("weightKg");
        String calStr = req.getParameter("caloriesIntake");

        if (dateStr == null || weightStr == null || calStr == null) {
            throw new ServletException("Missing form data");
        }

        String date = req.getParameter("date");
        double weight = Double.parseDouble(req.getParameter("weightKg"));
        int calories = Integer.parseInt(req.getParameter("caloriesIntake"));

        DailyLog log = new DailyLog(user.getUserId(), LocalDate.parse(date), weight, calories);
        try {
            logService.addOrUpdateLog(log);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            logService.deleteLog(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}