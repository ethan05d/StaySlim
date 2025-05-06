package servlet;

import model.CheckIn;
import model.User;
import service.CheckInService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CheckInServlet extends HttpServlet {
    private final CheckInService ciService = new CheckInService();

    // handle daily check-in requests (only POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // get the logged-in user from session
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");

        // build a CheckIn object for today
        CheckIn ci = new CheckIn(user.getUserId(), LocalDate.now());

        try {
            // record the check-in and update leaderboard
            ciService.recordCheckIn(ci);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
