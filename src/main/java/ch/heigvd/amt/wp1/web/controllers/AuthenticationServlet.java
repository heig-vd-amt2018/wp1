package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet illustrates various aspects of the Servlet API.
 * <p>
 * Firstly, notice that GET and POST requests are supported and that the same
 * code is invoked in both cases. In this application, /auth is invoked at two
 * occasions:
 * <p>
 * 1) when the user fills out the login form and presses the "Login" button.
 * This sends a POST request, with the content of the input fields transmitted
 * in parameters (there is also a HIDDEN field named 'action' with a value of
 * 'login').
 * <p>
 * 2) when the user clicks the "Logout" link in the navigation header. This
 * sends a GET request, with the value of the 'action' parameter set to 'logout'
 * in the query string (/auth?action=logout).
 * <p>
 * Secondly, it shows how to put an object (in this case a String, but it could
 * be a POJO) in the session. This object is then available to servlets and JSPs
 * as long as the session is active.
 * <p>
 * Thirdly, it shows how to terminate the session (which is important not only
 * to make sure that the user is logged out, but also to preserve resources on
 * the server). Even if sessions are automatically terminated after a
 * configurable idle time (typically 30 minutes), it is better to free allocated
 * memory as soon as possible.
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/auth"})
public class AuthenticationServlet extends HttpServlet {

    @EJB
    private UsersDAOLocal usersDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (action != null && (action.equals("logout") || (email != null && password != null))) {
            // When the user is not logged in yet and tries to access /pages/xxx, then he
            // is redirected to the login page by the security filter. The security filter
            // stores the targer url (/pages/xxx) in the request context, so that we can
            // send redirect the user to the desired location after successful authentication.
            //
            // If the user accessed /auth directly and there is no targetUrl, then we send him
            // to the home page.
            String targetUrl = (String) request.getAttribute("targetUrl");

            if (targetUrl == null) {
                targetUrl = "/pages/home";
            }

            targetUrl = request.getContextPath() + targetUrl;

            if ("login".equals(action)) {
                User user = null;

                try {
                    user = usersDAO.findByEmail(email);
                } catch (BusinessDomainEntityNotFoundException e) {
                    // Continue
                }

                if (user == null || !user.getPassword().equals(password)) {
                    request.setAttribute("alert", new ErrorAlert("Username or password incorrect."));
                    request.setAttribute("email", email);
                    request.setAttribute("password", password);
                    request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                } else if (user.getState().equals(User.State.DISABLED)) {
                    request.setAttribute("alert", new WarningAlert("This account has been disabled."));
                    request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                } else {
                    // Save the user in the session
                    request.getSession().setAttribute("principal", user);
                    response.sendRedirect(targetUrl);
                }
            } else if ("logout".equals(action)) {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath());
            } else {
                response.sendRedirect(targetUrl);
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}