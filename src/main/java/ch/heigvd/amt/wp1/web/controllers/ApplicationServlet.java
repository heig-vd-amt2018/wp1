package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApplicationServlet", urlPatterns = {"/pages/applications"})
public class ApplicationServlet extends HttpServlet {

    @EJB
    private ApplicationsDAOLocal applicationsDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("application", applicationsDAO.findAll());
        request.setAttribute("pageTitle", "Applications");
        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }
}
