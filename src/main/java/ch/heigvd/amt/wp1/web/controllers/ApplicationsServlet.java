package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;
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

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appName = request.getParameter("appName");
        String appDescription = request.getParameter("appDescription");

        if (appName != null && appDescription != null) {
            User user = (User) request.getSession().getAttribute("principal");
            Application application = null;

            try {
                // Check if the user already has an app with the same name
                application = applicationsDAO.findByNameByDeveloper(appName, user);
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }
            if (application == null && !appName.isEmpty()) {
                applicationsDAO.create(new Application(user, appName, appDescription));

                request.setAttribute("alert", new SuccessAlert("Application has been successfully created."));
            } else {
                request.setAttribute("appName", appName);
                request.setAttribute("appDescription", appDescription);
                request.setAttribute("error", true);

                if (appName.isEmpty()) {
                    request.setAttribute("alert", new WarningAlert("Application name is required."));
                } else {
                    request.setAttribute("alert", new WarningAlert("Another application has the same name. Please change."));
                }
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
        }

        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appIdParam = request.getParameter("appId");

        if (appIdParam != null) {

            User user = (User) request.getSession().getAttribute("principal");
            Application application = null;

            long appId = Long.parseLong(appIdParam);


            try {
                // Check if the user owns the application
                if (user.getRole() == User.Role.ADMINISTRATOR) {
                    application = applicationsDAO.findById(appId);
                } else {
                    application = applicationsDAO.findByIdByDeveloper(appId, user);
                }
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }

            if (application != null) {
                ApplicationDTO dto = new ApplicationDTO(application);

                request.setAttribute("application", dto);

                request.getRequestDispatcher("/WEB-INF/pages/application.jsp").forward(request, response);
            } else {
                request.setAttribute("alert", new ErrorAlert("Application not found."));

                request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appIdParam = request.getParameter("appId");
        String appName = request.getParameter("appName");
        String appDescription = request.getParameter("appDescription");

        if (appIdParam != null && appName != null && appDescription != null) {
            User user = (User) request.getSession().getAttribute("principal");
            Application application = null;

            long appId = Long.parseLong(appIdParam);

            try {
                // Check if the user owns the application
                if (user.getRole() == User.Role.ADMINISTRATOR) {
                    application = applicationsDAO.findById(appId);
                } else {
                    application = applicationsDAO.findByIdByDeveloper(appId, user);
                }
            } catch (NumberFormatException | BusinessDomainEntityNotFoundException e) {
                // Continue
            }

            if (application != null && !appName.isEmpty()) {

                application.setName(appName);
                application.setDescription(appDescription);

                Application updatedApplication = null;
                try {
                    if (user.getRole() == User.Role.ADMINISTRATOR) {
                        updatedApplication = applicationsDAO.findByNameByDeveloper(appName, application.getOwner());
                    } else {
                        updatedApplication = applicationsDAO.findByNameByDeveloper(appName, user);
                    }
                } catch (BusinessDomainEntityNotFoundException e) {
                    request.setAttribute("alert", new ErrorAlert("Application not found."));
                }

                if (updatedApplication == null || application.equals(updatedApplication)) {
                    try {
                        applicationsDAO.update(application);
                        request.setAttribute("alert", new SuccessAlert("Application has been successfully updated."));
                    } catch (BusinessDomainEntityNotFoundException e) {
                        request.setAttribute("alert", new SuccessAlert("Application has not been successfully updated."));
                    }
                } else {
                    request.setAttribute("alert", new WarningAlert("Another application has the same name. Please change."));
                }

                request.setAttribute("application", application);

            } else {
                request.setAttribute("appName", appName);
                request.setAttribute("appDescription", appDescription);

                if (appName.isEmpty()) {
                    request.setAttribute("alert", new WarningAlert("Application name is required."));
                } else {
                    request.setAttribute("alert", new ErrorAlert("Application not found."));
                }
            }

            request.getRequestDispatcher("/WEB-INF/pages/application.jsp").forward(request, response);
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appIdParam = request.getParameter("appId");

        if (appIdParam == null) {
            User user = (User) request.getSession().getAttribute("principal");
            Application application = null;

            long appId = Long.parseLong(appIdParam);

            try {
                // Check if the user owns the application
                if (user.getRole() == User.Role.ADMINISTRATOR) {
                    application = applicationsDAO.findById(appId);
                } else {
                    application = applicationsDAO.findByIdByDeveloper(appId, user);
                }
            } catch (BusinessDomainEntityNotFoundException e) {
                // Continue
            }

            if (application != null) {
                try {
                    applicationsDAO.delete(application);

                    request.setAttribute("alert", new SuccessAlert("Application has been successfully deleted."));

                    request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
                } catch (BusinessDomainEntityNotFoundException e) {
                    request.setAttribute("alert", new ErrorAlert("Application has not been successfully deleted."));

                    read(request, response);
                }
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String appId = request.getParameter("appId");

        action = action == null ? "" : action;
        appId = appId == null ? "" : appId;

        if (action.equals("save") && appId.isEmpty()) {
            create(request, response);
        } else if (action.equals("update") && !appId.isEmpty()) {
            update(request, response);
        } else if (action.equals("delete") && !appId.isEmpty()) {
            delete(request, response);
        } else if (action.isEmpty() && !appId.isEmpty()) {
            read(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
