package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.UserDTO;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.SuccessAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

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
    private UsersDAOLocal usersDAO;

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        String userFirstName = request.getParameter("userFirstName");
        String userLastName = request.getParameter("userLastName");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userRole = request.getParameter("userRole");
        String userState = request.getParameter("userState");

        User.State state = null;
        User.Role role = null;

        if (userState.equals(User.State.ENABLED.toString())) {
            state = User.State.ENABLED;
        } else if (userState.equals(User.State.DISABLED.toString())) {
            state = User.State.DISABLED;
        } else if (userState.equals(User.State.RESET.toString())) {
            state = User.State.RESET;
        }

        if (userRole.equals(User.Role.ADMINISTRATOR.toString())) {
            role = User.Role.ADMINISTRATOR;
        } else if (userRole.equals(User.Role.APPLICATION_DEVELOPER.toString())) {
            role = User.Role.APPLICATION_DEVELOPER;
        }
        try {
            if (usersDAO.findByEmail(userEmail) == null) {
                user = new User(userFirstName, userLastName, userEmail, userPassword, role, state, null);

                usersDAO.create(user);

                request.setAttribute("alert", new SuccessAlert("User has been successfully created."));
                request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
            } else {
                request.setAttribute("alert", new ErrorAlert("Email address already exist."));
                request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
            }
        } catch (BusinessDomainEntityNotFoundException e) {
            //continue
        }
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        long userId = Long.parseLong(request.getParameter("userId"));

        try {
            // Check if the user owns the user
            user = usersDAO.findById(userId);
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        if (user != null) {
            UserDTO dto = new UserDTO(user);

            request.setAttribute("user", dto);

            request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
        } else {
            request.setAttribute("alert", new ErrorAlert("User not found."));

            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        long userId = Long.parseLong(request.getParameter("userId"));

        String userFirstName = request.getParameter("userFirstName");
        String userLastName = request.getParameter("userLastName");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userRole = request.getParameter("userRole");
        String userState = request.getParameter("userState");

        User.State state = null;
        User.Role role = null;

        if (userState.equals(User.State.ENABLED.toString())) {
            state = User.State.ENABLED;
        } else if (userState.equals(User.State.DISABLED.toString())) {
            state = User.State.DISABLED;
        } else if (userState.equals(User.State.RESET.toString())) {
            state = User.State.RESET;
        }

        if (userRole.equals(User.Role.ADMINISTRATOR.toString())) {
            role = User.Role.ADMINISTRATOR;
        } else if (userRole.equals(User.Role.APPLICATION_DEVELOPER.toString())) {
            role = User.Role.APPLICATION_DEVELOPER;
        }
        try {
            user = usersDAO.findById(userId);
        } catch (BusinessDomainEntityNotFoundException e) {
            //request.setAttribute("alert", new ErrorAlert("User not found."));
        }

        if (
                (user != null) &&
                        (userFirstName != null) &&
                        (userLastName != null) &&
                        (userEmail != null) &&
                        (userPassword != null) &&
                        (role != null) &&
                        (state != null) &&
                        !userFirstName.isEmpty() &&
                        !userLastName.isEmpty() &&
                        !userPassword.isEmpty()

        ) {

            user.setFirstName(userFirstName);
            user.setLastName(userLastName);
            user.setEmail(userEmail);
            user.setPassword(userPassword);
            user.setRole(role);
            user.setState(state);


            try{
                usersDAO.update(user);
                request.setAttribute("alert", new SuccessAlert("User has been successfully updated."));
            } catch (BusinessDomainEntityNotFoundException e2) {
                request.setAttribute("alert", new ErrorAlert("User has not been successfully updated."));
            }

            request.setAttribute("user", user);

        } else {
            request.setAttribute("userFirstName", userFirstName);
            request.setAttribute("userLastName", userLastName);
            request.setAttribute("userEmail", userEmail);
            request.setAttribute("userPassword", userPassword);
            request.setAttribute("userRole", userRole);
            request.setAttribute("userState", userState);

            request.setAttribute("alert", new ErrorAlert("User not found."));
        }

        request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        long userId = Long.parseLong(request.getParameter("userId"));

        try {
            // Check if the user owns the user
            user = usersDAO.findById(userId);
        } catch (BusinessDomainEntityNotFoundException e) {
            // Continue
        }

        if (user != null) {
            try {
                usersDAO.delete(user);

                request.setAttribute("alert", new SuccessAlert("User has been successfully deleted."));

                request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
            } catch (BusinessDomainEntityNotFoundException e) {
                request.setAttribute("alert", new ErrorAlert("User has not been successfully deleted."));

                read(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String userId = request.getParameter("userId");

        action = action == null ? "" : action;
        userId = userId == null ? "" : userId;

        if (action.equals("save") && userId.isEmpty()) {
            create(request, response);
        } else if (action.equals("update") && !userId.isEmpty()) {
            update(request, response);
        } else if (action.equals("delete") && !userId.isEmpty()) {
            delete(request, response);
        } else if (action.isEmpty() && !userId.isEmpty()) {
            read(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
