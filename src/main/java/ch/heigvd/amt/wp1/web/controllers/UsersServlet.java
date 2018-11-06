package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDeveloperDTO;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.SuccessAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.ApplicationDevelopersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;
import ch.heigvd.amt.wp1.services.dao.GenericDAO;
import ch.heigvd.amt.wp1.services.dao.IGenericDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersServlet", urlPatterns = {"/pages/users"})
public class UsersServlet extends HttpServlet {

    @EJB
    private ApplicationDevelopersDAOLocal appDevDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        try {
            Long appId = Long.parseLong(request.getParameter("userId"));

            user = (User) appDevDAO.findById(appId);
        } catch (NumberFormatException | BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        if(user != null){
            ApplicationDeveloperDTO dto = new ApplicationDeveloperDTO();
            dto.fromEntity(user);
            request.setAttribute("user", dto);
            request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }
}
