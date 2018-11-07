package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Home page.
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/pages/home"})
public class HomeServlet extends HttpServlet {

    @EJB
    ApplicationsDAOLocal applicationsDAO;

    @EJB
    UsersDAOLocal usersDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("principal");

        if (user.getRole() == User.Role.ADMINISTRATOR) {
            long userNumbers = usersDAO.count();

            request.setAttribute("userNumbers", userNumbers);
        }

        long appNumbers = applicationsDAO.count();
        request.setAttribute("appNumbers", appNumbers);

        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
