package ch.heigvd.amt.wp1.web.controllers;

import ch.heigvd.amt.wp1.services.OrderServiceLocal;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FrontControllerServlet", urlPatterns = "/pages/front")
public class FrontControllerServlet extends javax.servlet.http.HttpServlet {

    @EJB
    OrderServiceLocal orderService;

    @EJB
    UsersDAOLocal user;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    long numberOfUserBefore= -1;
    long numberOfUSerAfter = -1;

    try {
        numberOfUserBefore = user.count();
        orderService.start();
    } catch (Exception e) {
        response.getWriter().println("There was a problem");
    }finally {
        numberOfUSerAfter = user.count();
        response.getWriter().println(String.format("Number of users before: %d", numberOfUserBefore));
        response.getWriter().println(String.format("Number of users after: %d", numberOfUSerAfter));
    }
  }
}
