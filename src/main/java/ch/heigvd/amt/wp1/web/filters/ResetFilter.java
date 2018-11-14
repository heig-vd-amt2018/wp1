package ch.heigvd.amt.wp1.web.filters;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * When a user has to reset its password, it has to do it now.
 */
@WebFilter(filterName="ResetFilter")
public class ResetFilter implements Filter {

    /**
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     * @throws IOException if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        User principal = (User) httpRequest.getSession().getAttribute("principal");

        if (principal != null && principal.getState() == User.State.RESET && !path.startsWith("/pages/profile")) {
            request.setAttribute("alert", new WarningAlert("You must reset your password."));
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
        } else if (principal != null && principal.getState() == User.State.RESET && path.startsWith("/pages/profile")) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

