package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        ApplicationDeveloper user = null;

        try {
            user = applicationDevelopersDAO.findByEmail(email);
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        if (user != null) {
            request.setAttribute("error", "User already registered. Please login with its password.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } else if (!password.equals(passwordConfirmation)) {
            request.setAttribute("error", "Passwords do not match.");
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
