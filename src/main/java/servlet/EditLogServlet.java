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

    // Show the edit form pre-populated with the existing log data
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // get the logID from query parameter
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }
        try {
            int id = Integer.parseInt(idParam);
            DailyLog log = logService.findById(id);
            if (log == null) {
                // if no such log, go back to dashboard
                resp.sendRedirect(req.getContextPath() + "/app/dashboard");
                return;
            }

            // put the DailyLog into request for the JSP
            req.setAttribute("log", log);
            req.getRequestDispatcher("/WEB-INF/views/editLog.jsp").forward(req, resp);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }

    // Process the form POST to save changes to the log
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // user must be logged in
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            // parse all form parameters
            int id = Integer.parseInt(req.getParameter("id"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            double weightKg = Double.parseDouble(req.getParameter("weightKg"));
            int calories = Integer.parseInt(req.getParameter("caloriesIntake"));

            // build a DailyLog object with updated values
            DailyLog log = new DailyLog();
            log.setLogId(id);
            log.setUserId(user.getUserId());
            log.setLogDate(date);
            log.setWeightKg(weightKg);
            log.setCaloriesIntake(calories);

            // call service to perform the update
            logService.addOrUpdateLog(log);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
