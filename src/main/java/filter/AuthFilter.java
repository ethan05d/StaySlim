package filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

// Filter to block access to protected URLs when the user is not logged in
public class AuthFilter implements Filter {

    // Init method when filter is first created
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    // Main entry point: runs before the request reaches servlets or JSPs
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // try to get an existing session
        HttpSession session = req.getSession(false);

        // Checks if the user is not logged in
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // user is authenticated, so allow the request to proceed
        chain.doFilter(request, response);
    }

    // Called once when the filter is taken out of service
    @Override
    public void destroy() {}
}
