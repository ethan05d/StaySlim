package servlet;

import model.Leaderboard;
import model.User;
import service.LeaderboardService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardServlet extends HttpServlet {
    private final LeaderboardService lbService = new LeaderboardService();
    private final UserService userService = new UserService();

    // Show the top users by streak and highlight the current user
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // get top 10 streak entries
            List<Leaderboard> raw = lbService.getTopStreaks(10);
            HttpSession session = req.getSession(false);
            int me = ((User) session.getAttribute("user")).getUserId();

            // transform into LeaderEntry objects
            List<LeaderEntry> entries = new ArrayList<>();
            int rank = 1;
            for (Leaderboard lb: raw) {
                User u = userService.findById(lb.getUserId());

                String name = "Unknown";
                if (u != null) {
                    name = u.getUsername();
                }

                // mark if this row is the logged-in user
                boolean isMe = lb.getUserId() == me;
                entries.add(new LeaderEntry(
                        rank++,
                        name,
                        lb.getCurrentStreak(),
                        lb.getMaxStreak(),
                        isMe
                ));
            }

            req.setAttribute("leaderEntries", entries);
            req.getRequestDispatcher("/WEB-INF/views/leaderboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
