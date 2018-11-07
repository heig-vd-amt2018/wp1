package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.AdministratorsDAOLocal;
import ch.heigvd.amt.wp1.services.dao.ApplicationDevelopersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

    @EJB
    ApplicationDevelopersDAOLocal applicationDevelopersDAO;

    @EJB
    AdministratorsDAOLocal administratorsDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        User user = null;

        try {
            user = applicationDevelopersDAO.findByEmail(email);
        } catch (BusinessDomainEntityNotFoundException e) {
            // The user has not been found as a regular user.
            // Will try to find the user as an administrator user.
            try {
                user = administratorsDAO.findByEmail(email);
            } catch (BusinessDomainEntityNotFoundException e1) {
                // Continue
            }
        }

        if (user != null) {
            request.setAttribute("alert", new WarningAlert("User already registered. Please login with its password."));
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } else if (password.isEmpty()) {
            request.setAttribute("alert", new ErrorAlert("Password can't be empty."));
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } else if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("alert", new ErrorAlert("Missing value for required field(s)"));
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } else if (!password.equals(passwordConfirmation)) {
            request.setAttribute("alert", new ErrorAlert("Passwords do not match."));
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } else {
            ApplicationDeveloper newUser = new ApplicationDeveloper(firstName, lastName, email, password, User.State.ENABLED, null);

            applicationDevelopersDAO.create(newUser);

            request.getSession().setAttribute("principal", newUser);

            String targetUrl = request.getContextPath() + "/pages/home";

            response.sendRedirect(targetUrl);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
