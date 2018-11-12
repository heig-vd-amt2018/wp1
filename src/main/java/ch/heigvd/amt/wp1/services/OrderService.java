package ch.heigvd.amt.wp1.services;
import ch.heigvd.amt.wp1.model.entities.Application;
import ch.heigvd.amt.wp1.model.entities.User;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAO;
import ch.heigvd.amt.wp1.services.dao.ApplicationsDAOLocal;
import ch.heigvd.amt.wp1.services.dao.BusinessDomainEntityNotFoundException;
import ch.heigvd.amt.wp1.services.dao.UsersDAOLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class OrderService implements OrderServiceLocal {

  @EJB
  UsersDAOLocal usersDAO;

  @EJB
  ApplicationsDAOLocal applicationsDAO;

  private int cpt = 0;

  public OrderService() {}

  public void start() {

    User user = new User("user" + cpt,"lastname","user"+cpt+"@gmail.com","pass", User.Role.APPLICATION_DEVELOPER,null);
    cpt++;

    try {
      //Create a user
      usersDAO.create(user);

      //Create applications
      applicationsDAO.create(new Application(user,"Title1","Des1"));
      applicationsDAO.create(new Application(user,"Title2","Des2"));
      applicationsDAO.create(new Application(user,"Title3","Des3"));
      applicationsDAO.create(new Application(user,"Title4","Des4"));
      applicationsDAO.create(new Application(user,"Title5","Des5"));
      applicationsDAO.create(new Application(user,"Title6","Des6"));
      applicationsDAO.create(new Application(user,"Title7","Des7")); //This line create a error*/

    } catch (Exception e) {
      System.out.println("EJB Facade has swallowed exception");
      throw(e);
    }
  }
}
