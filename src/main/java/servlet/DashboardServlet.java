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

    // render the user's dashboard with logs, streaks, BMI, etc.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // ensure the user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        try {
            // fetch all the user info
            List<DailyLog> logs = logService.getLogsForUser(user.getUserId());
            Leaderboard lb = lbService.getForUser(user.getUserId());

            boolean checkedInToday = checkInService.hasCheckedIn(user.getUserId(), LocalDate.now());
            req.setAttribute("checkedInToday", checkedInToday);

            req.setAttribute("logs", logs);
            req.setAttribute("leaderboard", lb);

            // calculate BMI based on the last log entry
            if (!logs.isEmpty()) {
                DailyLog last = logs.get(logs.size() - 1);
                double heightM = user.getHeightCm() / 100.0;
                double bmi = last.getWeightKg() / (heightM * heightM);
                double bmiRounded = Math.round(bmi * 10) / 10.0;

                req.setAttribute("bmiFormatted", "" + bmiRounded);
                req.setAttribute("lastWeightDate", last.getLogDate());
                req.setAttribute("lastWeightKg", last.getWeightKg());

            } else {
                // no logs yet
                req.setAttribute("bmiFormatted", null);
            }

            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
