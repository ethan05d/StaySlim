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

    // handle form POST for creating or updating a daily log
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // get user from session
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");

        String dateStr = req.getParameter("date");
        String weightStr = req.getParameter("weightKg");
        String calStr = req.getParameter("caloriesIntake");

        // checking if the strings are null for form submission
        if (dateStr == null || weightStr == null || calStr == null) {
            throw new ServletException("Missing form data");
        }

        double weight = Double.parseDouble(weightStr);
        int calories = Integer.parseInt(calStr);
        LocalDate date = LocalDate.parse(dateStr);

        DailyLog log = new DailyLog(user.getUserId(), date, weight, calories);
        try {
            // add new or update existing log
            logService.addOrUpdateLog(log);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // handle delete requests for a specific log entry
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // parse the logID from query parameter
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            // delete the log using service
            logService.deleteLog(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
