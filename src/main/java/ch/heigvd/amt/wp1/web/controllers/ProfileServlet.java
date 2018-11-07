package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.SuccessAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This a very simple controller. There is no service to invoke, no model to
 * prepare for the view. We simply delegate rendering of a static view to a
 * JSP page.
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/pages/profile"})
public class ProfileServlet extends HttpServlet {

    @EJB
    UsersDAOLocal usersDAO;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        User user = (User)request.getSession().getAttribute("principal");

        if (password.isEmpty()) {
            request.setAttribute("alert", new ErrorAlert("Password can't be empty."));
        } else if(firstName.isEmpty() || lastName.isEmpty()) {
            request.setAttribute("alert", new ErrorAlert("Missing value for required field(s)"));
        } else if (!password.equals(passwordConfirmation)) {
            request.setAttribute("alert", new ErrorAlert("Passwords do not match."));
        } else {

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setState(User.State.ENABLED);

            try {
                usersDAO.update(user);
                request.setAttribute("alert", new SuccessAlert("Profile has been successfully updated."));
            } catch (BusinessDomainEntityNotFoundException e) {
                request.setAttribute("alert", new ErrorAlert("Profile has not been successfully updated."));
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }
}
