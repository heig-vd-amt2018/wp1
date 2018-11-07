package ch.heigvd.amt.wp1.web.filters;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Only allows application developers to access certain pages
 */
@WebFilter(filterName="ApplicationDeveloperFilter")
public class ApplicationDeveloperFilter implements Filter {

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

        if (path.startsWith("/api/application") || path.startsWith("/pages/application")) {

            User principal = (User) httpRequest.getSession().getAttribute("principal");

            if (principal.getRole() != User.Role.APPLICATION_DEVELOPER) {
                request.setAttribute("error", new ErrorAlert("You do not have the rights to access this page."));
                request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
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

