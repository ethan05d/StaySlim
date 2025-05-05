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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        int id = req.getParameter("id")!=null?Integer.parseInt(req.getParameter("id")):0;
        if (req.getParameter("_method")!=null && req.getParameter("_method").equals("delete")) {
            try { goalService.deleteGoal(id); } catch(SQLException e){throw new ServletException(e);}
        } else {
            double tw = Double.parseDouble(req.getParameter("targetWeightKg"));
            int tc = Integer.parseInt(req.getParameter("targetCalories"));
            LocalDate sd = LocalDate.parse(req.getParameter("startDate"));
            LocalDate ed = LocalDate.parse(req.getParameter("endDate"));
            Goal g = new Goal(user.getUserId(), tw, tc, sd, ed);
            try { goalService.addGoal(g); } catch(SQLException e){throw new ServletException(e);}
        }
        resp.sendRedirect(req.getContextPath()+"/app/goals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // to display goals page
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        try {
            req.setAttribute("goals", goalService.getGoalsForUser(user.getUserId()));
            req.getRequestDispatcher("/WEB-INF/views/goals.jsp").forward(req, resp);
        } catch(SQLException e) { throw new ServletException(e);}
    }
}