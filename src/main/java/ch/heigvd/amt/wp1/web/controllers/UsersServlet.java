package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.dao.ApplicationDevelopersDAOLocal;
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
    private ApplicationDevelopersDAOLocal AppDevDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
}
