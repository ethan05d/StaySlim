package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    // Handle logout by invalidating the user's session
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // fetch existing session without creating a new one
        HttpSession session = req.getSession(false);

        if (session != null) {
            // remove all session attributes and mark it invalid
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
