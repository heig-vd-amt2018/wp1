# Transactions 

Ludovic Delafontaine Théo Gallandat Joël Kaufmann Xavier Vaz Afonso

## Experiances

### First Experiance

Create a user (First transaction).

Create a list of applications (Second transaction).

When an application generate a error. The **user** and **all of the application** are rollback. The data is not saved.

If no exception is thrown, the user and the applications are saved on the database.

### Second Experiance

Create a user (First transaction).

Create a list of applications (Second transaction).

When an application generate a error. **Only applications** are rollback. The user is saved on the database.

If no exception is thrown, the user and the applications are saved on the database.

## Explanation

These experiances are only examples to prove that the rollback is working.

A better example would be to create a list of application with a user that don't exist yet. Create the specific user, if the user throw a error, the user and the applications are rollback.

However, we made sure that for the creation of an application that it must already have an user on the database.

Another solution would be to add a new field on the database  However, we thought that it wasn't a good idea if it was just for this scenario.

## How to test it ?

- Log in with the admin account
- Go the the /pages/transactions
- Refresh the page
  - The number of users before the insertion
  - The number of user after the insertion

If the number are the same, that's means that the rollback has work.

Else that the user for the first experiance or user and applications for the second experiance was created on the database.

## Codes

*TransactionsControllerServlet*

Count the user before and after the insertion on the database.

```java

@WebServlet(name = "TransactionsControllerServlet", urlPatterns = "/pages/transactions")
public class TransactionsControllerServlet extends javax.servlet.http.HttpServlet {

    @EJB
    CheckTransactionsLocal orderService;

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

```

*GenericDAO*

Generate the error after the creation of the application

```java
    @Override
    public PK create(T t) {
        T managedEntity = createAndReturnManagedEntity(t);
        PK id = managedEntity.getId();

        if(t instanceof Application){

            // If the title is Title7, throw un exception
            if(((Application)t).getName().equals("Title7") ) {
                //throw new RuntimeException("boum");
            }
        }
        return id;
    }
```

*UserDAO*

Create the user and the applications

To execute the first experiance, disable the line TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

```java
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long create(User user) {
        System.out.println("Create User " + user.getEmail());
        return super.create(user);
    }
```

*ApplicationDAO*

```java
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ApplicationsDAO extends GenericDAO<Application, Long> implements ApplicationsDAOLocal
```

*CheckTransactions*

Create the user and applications.

```java
@Stateless
public class CheckTransactions implements CheckTransactionsLocal {

  @EJB
  UsersDAOLocal usersDAO;

  @EJB
  ApplicationsDAOLocal applicationsDAO;

  private static int cpt = 0;

  public CheckTransactions() {}

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
      applicationsDAO.create(new Application(user,"Title7","Des7")); //This line create a error

    } catch (Exception e) {
      System.out.println("EJB Facade has swallowed exception");
      throw new RuntimeException("boum");
    }
  }
}

```


