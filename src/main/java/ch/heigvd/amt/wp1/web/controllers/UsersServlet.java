package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.rest.dto.UserDTO;
import ch.heigvd.amt.wp1.services.business.errors.ErrorAlert;
import ch.heigvd.amt.wp1.services.business.errors.SuccessAlert;
import ch.heigvd.amt.wp1.services.business.errors.WarningAlert;
import ch.heigvd.amt.wp1.services.business.mail.EmailSender;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "UsersServlet", urlPatterns = {"/pages/users"})
public class UsersServlet extends HttpServlet {

    @EJB
    private UsersDAOLocal usersDAO;

    @EJB
    private EmailSender emailSender;

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user;
        User userVerifEmail;
        boolean emailOk = true;

        String userFirstName = request.getParameter("userFirstName");
        String userLastName = request.getParameter("userLastName");
        String userEmail = request.getParameter("userEmail");
        String userRole = request.getParameter("userRole");

        if (userFirstName != null
                && userLastName != null
                && userEmail != null
                && userRole != null) {

            boolean emailValid = true;

            try {
                InternetAddress emailAddress = new InternetAddress(userEmail);
                emailAddress.validate();
            } catch (AddressException ex) {
                emailValid = false;
            }

            if (emailValid) {
                User.Role role = null;

                if (userRole.equals(User.Role.ADMINISTRATOR.toString())) {
                    role = User.Role.ADMINISTRATOR;
                } else if (userRole.equals(User.Role.APPLICATION_DEVELOPER.toString())) {
                    role = User.Role.APPLICATION_DEVELOPER;
                }

                try {
                    userVerifEmail = usersDAO.findByEmail(userEmail);
                    emailOk = userVerifEmail == null;
                } catch (BusinessDomainEntityNotFoundException e) {
                    //continue
                }

                if (!userFirstName.isEmpty()
                        && !userLastName.isEmpty()
                        && !userEmail.isEmpty()
                        && !userRole.isEmpty()
                ) {
                    if (emailOk) {
                        String newPassword = UUID.randomUUID().toString();

                        user = new User(userFirstName, userLastName, userEmail, newPassword, role, null);

                        try {
                            // Send the mail
                            emailSender.sendEmail(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());

                            // Create the user only if the mail is send.
                            usersDAO.create(user);
                            request.setAttribute("alert", new SuccessAlert("User created."));
                        } catch (MessagingException e) {
                            request.setAttribute("alert", new ErrorAlert("User not created, mail sending failed, try again later."));
                            e.printStackTrace();
                        }
                    } else {
                        request.setAttribute("userFirstName", userFirstName);
                        request.setAttribute("userLastName", userLastName);
                        request.setAttribute("error", true);
                        request.setAttribute("alert", new WarningAlert("This email address already exist."));
                    }
                } else {
                    request.setAttribute("userFirstName", userFirstName);
                    request.setAttribute("userLastName", userLastName);
                    request.setAttribute("userEmail", userEmail);
                    request.setAttribute("error", true);
                    request.setAttribute("alert", new WarningAlert("All field should be filled."));
                }
            } else {
                request.setAttribute("alert", new ErrorAlert("Email is not valid."));
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
        }
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;
        long userId;

        String userIdParam = request.getParameter("userId");

        if (userIdParam != null) {

            userId = Long.parseLong(userIdParam);

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
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;
        User userVerifEmail = null;
        boolean emailOk = true;

        String userIdParam = request.getParameter("userId");
        String userFirstName = request.getParameter("userFirstName");
        String userLastName = request.getParameter("userLastName");
        String userEmail = request.getParameter("userEmail");
        String userRole = request.getParameter("userRole");
        String userState = request.getParameter("userState");

        if (
                userFirstName != null
                && userLastName != null
                && userEmail != null
                && userRole != null
                && userState != null
                && userIdParam != null
        ) {

            long userId = Long.parseLong(userIdParam);

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
                //Continue
            }

            try {
                userVerifEmail = usersDAO.findByEmail(userEmail);
                emailOk = userVerifEmail == null || userVerifEmail.getId() == userId;
            } catch (BusinessDomainEntityNotFoundException e) {
                //continue
            }

            if (user != null) {
                user.setFirstName(userFirstName);
                user.setLastName(userLastName);
                user.setRole(role);
                user.setState(state);

                request.setAttribute("user", user);

                if (!userFirstName.isEmpty()
                        && !userLastName.isEmpty()
                        && !userEmail.isEmpty()
                        && !userRole.isEmpty()
                        && !userState.isEmpty()
                ) {
                    if (emailOk) {
                        try {
                            usersDAO.update(user);
                            request.setAttribute("alert", new SuccessAlert("User has been successfully updated."));
                        } catch (BusinessDomainEntityNotFoundException e2) {
                            request.setAttribute("alert", new ErrorAlert("User has not been successfully updated."));
                        }
                    } else {
                        request.setAttribute("alert", new ErrorAlert("This email already used."));
                    }
                } else {
                    request.setAttribute("alert", new ErrorAlert("All fields should be filled."));
                }
            } else {
                request.setAttribute("alert", new ErrorAlert("User not found."));
            }
            request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);

        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = null;

        String userIdParam = request.getParameter("userId");

        if(userIdParam != null) {
            long userId = Long.parseLong(userIdParam);

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

        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
            request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
        }
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = null;

        String userIdParam = request.getParameter("userId");

        if(userIdParam != null) {
            long userId = Long.parseLong(userIdParam);
            try {
                user = usersDAO.findById(userId);
            } catch (BusinessDomainEntityNotFoundException e) {
                //continue
            }
            if (user != null) {

                if (user.getState() != User.State.RESET) {

                    user.setState(User.State.RESET);

                    String newPassword = UUID.randomUUID().toString();
                    user.setPassword(newPassword);

                    try {

                        try {
                            // Send the mail
                            emailSender.sendEmail(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());

                            // Update the user only if the mail is send.
                            usersDAO.update(user);
                            request.setAttribute("alert", new SuccessAlert("Password reset."));

                        } catch (MessagingException e) {
                            request.setAttribute("alert", new ErrorAlert("User not updated because mail not send."));
                            e.printStackTrace();
                        }

                        request.setAttribute("alert", new SuccessAlert("Password reset, email sent."));
                    } catch (BusinessDomainEntityNotFoundException e2) {
                        request.setAttribute("alert", new ErrorAlert("Error when updating user."));
                    }
                } else {
                    request.setAttribute("alert", new ErrorAlert("Password already reset."));
                }
            } else {
                request.setAttribute("alert", new ErrorAlert("Error when resetting password."));
            }
        } else {
            request.setAttribute("alert", new ErrorAlert("Missing parameters. Please verify your inputs."));
        }
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
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
        } else if (action.equals("resetPassword") && !userId.isEmpty()) {
            resetPassword(request, response);
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
