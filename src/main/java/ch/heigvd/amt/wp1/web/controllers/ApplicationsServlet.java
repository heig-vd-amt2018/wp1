package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.ApplicationDeveloper;
import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.SuccessAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApplicationsServlet", urlPatterns = {"/pages/applications"})
public class ApplicationsServlet extends HttpServlet {

    @EJB
    private ApplicationsDAOLocal applicationsDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Application application = null;

        try {
            Long appId = Long.parseLong(request.getParameter("appId"));

            application = applicationsDAO.findById(appId);
        } catch (NumberFormatException | BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        if (application != null) {
            ApplicationDTO dto = new ApplicationDTO();

            dto.fromEntity(application);
            
            request.setAttribute("application", dto);

            request.getRequestDispatcher("/WEB-INF/pages/application.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicationDeveloper user = (ApplicationDeveloper) request.getSession().getAttribute("principal");
        Application application = null;

        try {
            Long appId = Long.parseLong(request.getParameter("appId"));

            // Check if the user owns the application
            application = applicationsDAO.findByIdByDeveloper(appId, user);
        } catch (NumberFormatException | BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        String name = request.getParameter("appName");
        String description = request.getParameter("appDescription");

        if (name != null && description != null && !name.isEmpty()) {

            if (application != null) {
                application.setName(name);
                application.setDescription(description);

                try {
                    applicationsDAO.update(application);

                    request.setAttribute("alert", new SuccessAlert("Application has been succesfully updated."));
                } catch (BusinessDomainEntityNotFoundException e) {
                    request.setAttribute("alert", new ErrorAlert("Application has not been succesfully updated."));
                }
            } else {
                application = new Application(user, name, description);

                try {
                    applicationsDAO.findByNameByDeveloper(name, user);

                    request.setAttribute("alert", new WarningAlert("Another application has the same name. Please change."));
                    request.setAttribute("appName", name);
                    request.setAttribute("appDescription", description);
                } catch (BusinessDomainEntityNotFoundException e) {
                    applicationsDAO.create(application);

                    request.setAttribute("alert", new SuccessAlert("Application has been succesfully created."));
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicationDeveloper user = (ApplicationDeveloper) request.getSession().getAttribute("principal");
        Application application = null;

        try {
            String appIdString = request.getParameter("appId");
            Long appId = Long.parseLong(appIdString);

            // Check if the user owns the application
            application = applicationsDAO.findByIdByDeveloper(appId, user);
        } catch (NumberFormatException | BusinessDomainEntityNotFoundException e) {
            request.setAttribute("alert", new WarningAlert("You do not have the rights to do that."));
        }

        if (application != null) {
            try {
                applicationsDAO.delete(application);
                request.setAttribute("alert", new SuccessAlert("Application has been succesfully deleted."));
            } catch (BusinessDomainEntityNotFoundException e) {
                request.setAttribute("alert", new ErrorAlert("Application has not been succesfully deleted."));
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }
}
