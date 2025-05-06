package servlet;

import model.Goal;
import model.User;
import service.GoalService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class GoalServlet extends HttpServlet {
    private final GoalService goalService = new GoalService();

    // Handle creating or deleting a goal based on form parameters
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        // read "id" for delete or edit
        int id = 0;
        if (req.getParameter("id") != null) {
            id = Integer.parseInt(req.getParameter("id"));
        }

        // determine if this is a delete action
        if ("delete".equals(req.getParameter("_method"))) {
            try {
                goalService.deleteGoal(id);
            } catch (SQLException e) {
                throw new ServletException(e);
            }

        } else {
            // otherwise, read form fields to create a new goal
            double targetWeight = Double.parseDouble(req.getParameter("targetWeightKg"));
            int targetCalories = Integer.parseInt(req.getParameter("targetCalories"));
            LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
            LocalDate endDate   = LocalDate.parse(req.getParameter("endDate"));

            Goal g = new Goal(user.getUserId(), targetWeight, targetCalories, startDate, endDate);
            try {
                goalService.addGoal(g);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/app/goals");
    }

    // Display the user's goals on the goals page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            req.setAttribute("goals", goalService.getGoalsForUser(user.getUserId()));
            req.getRequestDispatcher("/WEB-INF/views/goals.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
