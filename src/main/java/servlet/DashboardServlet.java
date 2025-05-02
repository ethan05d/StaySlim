package servlet;

import model.DailyLog;
import model.Leaderboard;
import model.User;
import service.CheckInService;
import service.DailyLogService;
import service.LeaderboardService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashboardServlet extends HttpServlet {
    private final DailyLogService logService = new DailyLogService();
    private final LeaderboardService lbService = new LeaderboardService();
    private final CheckInService checkInService = new CheckInService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        try {
            // Fetch the user's logs and leaderboard entry
            List<DailyLog> logs = logService.getLogsForUser(user.getUserId());
            Leaderboard lb = lbService.getForUser(user.getUserId());

            // Checks if a user has checked in
            boolean checkedInToday = checkInService.hasCheckedIn(user.getUserId(), LocalDate.now());
            req.setAttribute("checkedInToday", checkedInToday);

            // Put them into request scope
            req.setAttribute("logs", logs);
            req.setAttribute("leaderboard", lb);

            // compute BMI for the last entry, if any
            if (!logs.isEmpty()) {
                DailyLog last = logs.get(logs.size() - 1);
                double heightM = user.getHeightCm() / 100.0;
                double bmi = last.getWeightKg() / (heightM * heightM);
                req.setAttribute("bmiFormatted", String.format("%.1f", bmi));
                req.setAttribute("lastWeightDate", last.getLogDate());
                req.setAttribute("lastWeightKg", last.getWeightKg());
            } else {
                req.setAttribute("bmiFormatted", null);
            }

            // Forward to JSP
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
