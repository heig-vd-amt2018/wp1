package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

    @EJB
    UsersDAOLocal usersDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        if (firstName != null && lastName != null && email != null && password != null && passwordConfirmation != null) {
            User user = null;

            try {
                user = usersDAO.findByEmail(email);
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }

            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);

            boolean emailValid = true;

            try {
                InternetAddress emailAddress = new InternetAddress(email);
                emailAddress.validate();
            } catch (AddressException ex) {
                emailValid = false;
            }

            if (user != null) {
                request.setAttribute("alert", new WarningAlert("Email already registered."));
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            } else if (!emailValid) {
                request.setAttribute("alert", new ErrorAlert("Email is not valid."));
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            } else if (password.isEmpty()) {
                request.setAttribute("alert", new ErrorAlert("Password can't be empty."));
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            } else if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                request.setAttribute("alert", new ErrorAlert("Missing value for required field(s)"));
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            } else if (!password.equals(passwordConfirmation)) {
                request.setAttribute("alert", new ErrorAlert("Passwords do not match."));
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            } else {
                User newUser = new User(firstName, lastName, email, password, User.Role.APPLICATION_DEVELOPER, User.State.ENABLED, null);

                usersDAO.create(newUser);

                request.getSession().setAttribute("principal", newUser);

                String targetUrl = request.getContextPath() + "/pages/home";

                response.sendRedirect(targetUrl);
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
    }
}
