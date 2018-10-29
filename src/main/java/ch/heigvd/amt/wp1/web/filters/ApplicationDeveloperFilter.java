package ch.heigvd.amt.wp1.web.filters;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class implements the Filter interface defined in the Servlet API. A
 * filter is a component that is placed in the (HTTP) request processing
 * pipeline. It can inspect and manipulate both the request (on the way in) and
 * the response (on the way out).
 * <p>
 * The responsibility of this class is to handle authorization of HTTP requests
 * issued by clients. The security policy is defined as follows: - all pages of
 * the application are protected and can be accessed only if the the user has
 * successfully authenticated. If that is the case, then there must be an object
 * named "principal" stored in the HTTP session. - static content (css,
 * javascript, etc.) is not protected - the login page, which displays the login
 * form, is not protected - the authentication servlet, which processes data
 * entered in the login form, is not protected either
 * <p>
 * These rules are enforced in the method. Note that there is no actual password
 * verification. Any password will be accepted in this illustrative scenario.
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@WebFilter(filterName="ApplicationDeveloperFilter")
public class ApplicationDeveloperFilter implements Filter {

    /**
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/api/application") || path.startsWith("/pages/application")) {

            User principal = (User) httpRequest.getSession().getAttribute("principal");

            if (principal.getRole() != User.Role.APPLICATION_DEVELOPER) {
                /*
                 * The user has not been authenticated and tries to access a protected resource,
                 * we display the login page (and interrupt the request processing pipeline).
                 */
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

