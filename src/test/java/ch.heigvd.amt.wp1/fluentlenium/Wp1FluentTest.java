package ch.heigvd.amt.wp1.fluentlenium;

import ch.heigvd.amt.wp1.fluentlenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 *  UI base tests using Fluentlenium<br>
 *  Workd with Chrome driver executable<br><br>
 *
 *  Requires default users {admin@wp1.ch, adminadmin} and
 *  {user1@wp1.ch, jeanbon, ENABLED}, {user2@wp1.ch, pierrequiroule, ENABLED}, {user3@wp1.ch, alainproviste, RESET}
 *  defined in src/main/resources/META-INF/sql/data.sql <br>
 */
public class Wp1FluentTest extends FluentTest {

  public static final String baseUrl = "http://localhost:8080/WP1-1.0-SNAPSHOT/";
  private static String OS = System.getProperty("os.name").toLowerCase();

  @Page
  public LoginFluentPage loginFluentPage;
  @Page
  public ProfileFluentPage profileFluentPage;
  @Page
  public RegistrationFluentPage registrationFluentPage;
  @Page
  public HomeFluentPage homeFluentPage;
  @Page
  public UsersFluentPage usersFluentPage;
  @Page
  public ApplicationsFluentPage appsFluentPage;

  /*
   *  REGISTRATION / LOGIN
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithoutInformation(){
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();  // register page : bad registration
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void successfulRegistrationShouldBringUserToHomePage() {
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("Jean");
    registrationFluentPage.typeLastName("Paul");
    registrationFluentPage.typeEmailAddress("jeanpaul@wp1.ch");
    registrationFluentPage.typePassword("test");
    registrationFluentPage.typeConfirmPassword("test");
    registrationFluentPage.clickSignin();
    homeFluentPage.isAt();  // home page : valid registration
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithTwoDifferentPassword(){
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("Richard");
    registrationFluentPage.typeLastName("Aldana");
    registrationFluentPage.typeEmailAddress("Richard.Aldana@wp1.ch");
    registrationFluentPage.typePassword("password");
    registrationFluentPage.typeConfirmPassword("wrong");  // different password
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();                        // register page : bad registration
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithAnExistingEmail() {
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("James");
    registrationFluentPage.typeLastName("Bond");
    registrationFluentPage.typeEmailAddress("user1@wp1.ch"); // default DB account created at each initialization
    registrationFluentPage.typePassword("password");
    registrationFluentPage.typeConfirmPassword("password");
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();  // register page : bad registration
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void successfulSigninShouldBringUserToHomePage() {
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("admin@wp1.ch");
    loginFluentPage.typePassword("adminadmin");
    loginFluentPage.clickSignin();
    homeFluentPage.isAt();  // home page : logged
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToSigninWithAnInvalidEmail() {
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("not a valid email");
    loginFluentPage.typePassword("any password");
    loginFluentPage.clickSignin();
    loginFluentPage.isAt();   // login page : not logged
  }


  /*
   *  ADMIN ACTIONS
   */
  // USER MANAGEMENT
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToCreateAUser() {
    adminLogin();

    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName("Bruce");
    usersFluentPage.typeLastName("Wayne");
    usersFluentPage.typeEmailAddress("brucewayne@wp1.ch");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    usersFluentPage.isCreated();  // alert message OK
    usersFluentPage.isOnceInTable("brucewayne@wp1.ch");  // found in table
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateAUserWithoutEmail() {
    adminLogin();

    // try creating empty user
    final String FIRST_NAME = "FirstName";
    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();       // users page
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName(FIRST_NAME);
    usersFluentPage.typeLastName("LastName");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    usersFluentPage.isNotInTable(FIRST_NAME);   // user not create
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateDuplicateUser() {
    adminLogin();

    // try creating existing user
    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();    // users page
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName("je");
    usersFluentPage.typeLastName("I");
    usersFluentPage.typeEmailAddress("user1@wp1.ch"); // already used
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    usersFluentPage.isAt();
    usersFluentPage.isPreventedFromDuplicateCreation();      // alert message OK
    usersFluentPage.isOnceInTable("user1@wp1.ch");  // counts only 1 in table
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToModifyUser() {
    adminLogin();

    final String modifiedLastName = "ModifiedLastName";
    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();                           // users page
    //usersFluentPage.clickModifyUser(1);        // modify second in table (default)
    goTo("http://localhost:8080/WP1-1.0-SNAPSHOT/pages/users?userId=2"); // modify user 1 in table (default)
    usersFluentPage.typeLastName(modifiedLastName);
    usersFluentPage.clickUpdate();
    usersFluentPage.isUpdated();                      // alert message OK
    usersFluentPage.goToUsersPage();                  // return to users page
    usersFluentPage.isOnceInTable(modifiedLastName);  // counts only 1 in table
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToResetUser() {
    adminLogin();

    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();                   // users page
    //usersFluentPage.clickModifyUser(2); // modify third in table (default)
    goTo("http://localhost:8080/WP1-1.0-SNAPSHOT/pages/users?userId=3"); // modify user 3 in table (default)
    usersFluentPage.clickReset();
    usersFluentPage.isReset();                // alert message OK
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToResetUserTwice() {
    adminLogin();

    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();                    // users page
    //usersFluentPage.clickModifyUser(2);
    goTo("http://localhost:8080/WP1-1.0-SNAPSHOT/pages/users?userId=3"); // modify user 3 in table (default)
    usersFluentPage.clickReset();
    usersFluentPage.isPreventedFromResettingTwice(); // alert message OK
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToDeleteUser() {
    adminLogin();

    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();                  // users page
    //usersFluentPage.clickModifyUser(3);// modify fourth in table (default)
    goTo("http://localhost:8080/WP1-1.0-SNAPSHOT/pages/users?userId=4"); // modify user 3 in table (default)
    usersFluentPage.clickDelete();
    usersFluentPage.isDeleted();              // alert message OK
    usersFluentPage.goToUsersPage();          // return to users page
    usersFluentPage.isNotInTable("user3@wp1.ch");
  }

  // APPLICATION MANAGEMENT
  private final String appName1 =  "Test app#1";
  private final String appName2 =  "Test app#2";
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToCreateApplication() {
    adminLogin();

    homeFluentPage.goToAppsPage();
    appsFluentPage.isAt();                  // applications page
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName(appName1);
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();                 // alert message OK
    appsFluentPage.isOnceInTable(appName1);     // counts once in table
  }

  // APPLICATION MANAGEMENT
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateDuplicateApplication() {
    adminLogin();

    // create new app first
    homeFluentPage.goToAppsPage();
    appsFluentPage.isAt();                // applications page
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName(appName2);
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    await().explicitlyFor(100);
    appsFluentPage.isCreated();             // alert message OK
    appsFluentPage.goToAppsPage();
    appsFluentPage.isOnceInTable(appName1); // counts once in table

    // duplicate app
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName(appName2);
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isPreventedFromDuplicateCreation();  // alert message OK
    appsFluentPage.goToAppsPage();
    appsFluentPage.isOnceInTable(appName1);             // counts only once in table
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToCreateAndModifyApplication() {
    adminLogin();

    final String oldAppName =  "AppToModifyWithSpaces";
    final String newAppName =  "App To Modify With Spaces";

    homeFluentPage.goToAppsPage();
    appsFluentPage.isAt();    // applications page

    // create app to be modified
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName(oldAppName);
    appsFluentPage.typeAppDescription("MyApp");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();

    // modify app
    appsFluentPage.isAt();
    appsFluentPage.clickModifyApp(0);
    appsFluentPage.typeAppName(newAppName);
    appsFluentPage.typeAppDescription("My App");
    appsFluentPage.clickSave();
    appsFluentPage.isModified();              // alert message OK
    appsFluentPage.goToAppsPage();            // return to apps
    appsFluentPage.isNotInTable(oldAppName);  // old named app should not be in table
    appsFluentPage.isOnceInTable(newAppName); // modified app should be in table
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToDeleteApplication() {
    adminLogin();

    homeFluentPage.goToAppsPage();
    appsFluentPage.isAt();   // applications page
    int appCount = appsFluentPage.countElemsInTable(); // counts apps

    if (appCount == 0) {
      // create app to have at least one app
      appsFluentPage.clickAddApp();
      appsFluentPage.typeAppName("App to delete");
      appsFluentPage.typeAppDescription(":(");
      appsFluentPage.clickCreateAppBtn();
      appsFluentPage.isCreated();
      appsFluentPage.isOnceInTable("App to delete");
      appCount++;
    }

    // delete first app in table
    appsFluentPage.clickModifyApp(0);       // modify - delete app
    appsFluentPage.clickDelete();
    appsFluentPage.isDeleted();                   // alert message OK
    appsFluentPage.goToAppsPage();
    //appsFluentPage.containsNElements(--appCount); // one app has been deleted
  }


  /*
   *  APP_DEVELOPER ACTIONS
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void resetUserMustSetAValidPassword() {
    // Sign in with RESET default user
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("user3@wp1.ch");
    loginFluentPage.typePassword("alainproviste");
    loginFluentPage.clickSignin();
    profileFluentPage.isAt();         // redirected to profile page
    profileFluentPage.isRedirected(); // alert message OK

    goTo(appsFluentPage);     // try getting to apps page
    profileFluentPage.isAt(); // page has not changed

    // impossible to set new empty password
    profileFluentPage.typePassword("");
    profileFluentPage.typeConfirmPassword("");
    profileFluentPage.clickSave();
    profileFluentPage.isPwdEmpty();   // alert message OK

    // set new valid password
    profileFluentPage.typePassword("onetwothree");
    profileFluentPage.typeConfirmPassword("onetwothree");
    profileFluentPage.clickSave();
    profileFluentPage.isPwdChanged();   // alert message OK

    profileFluentPage.goToAppsPage();   // try getting to apps page
    appsFluentPage.isAt();              // page has changed
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void appDevelopperShouldNotSeeUsers() {
    // Sign in as default appdev
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("user1@wp1.ch");
    loginFluentPage.typePassword("jeanbon");
    loginFluentPage.clickSignin();
    homeFluentPage.isAt();

    goTo(usersFluentPage.getUrl());         // try accessing users page
    homeFluentPage.isAt();                  // current page should be the home page
    homeFluentPage.isRedirectedOnRightsError(); // alert message OK
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void appDevelopperShouldBeAbleToCreateModifyAndDeleteIt() {
    // Sign in as default appdev
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("user1@wp1.ch");
    loginFluentPage.typePassword("jeanbon");
    loginFluentPage.clickSignin();
    homeFluentPage.isAt();

    final String oldName = "appToDelete";
    final String newName = "user#1 App";


    // create app
    homeFluentPage.goToAppsPage();
    appsFluentPage.isAt();              // current page should be apps page
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName(oldName);
    appsFluentPage.typeAppDescription("My first app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();                // alert message OK
    appsFluentPage.isOnceInTable(oldName);     // counts once in table

    // modify app
    appsFluentPage.clickModifyApp(0);
    appsFluentPage.typeAppName(newName);
    appsFluentPage.typeAppDescription("My first application");
    appsFluentPage.clickSave();
    appsFluentPage.isModified();               // alert message OK
    appsFluentPage.goToAppsPage();
    appsFluentPage.isOnceInTable(newName);     // counts once in table
    appsFluentPage.isNotInTable(oldName);      // old app name not in table

    // delete app
    appsFluentPage.clickModifyApp(0);
    appsFluentPage.clickDelete();
    appsFluentPage.isDeleted();                  // alert message OK
    appsFluentPage.goToAppsPage();
    appsFluentPage.isNotInTable(newName);        // old app name not in table
  }


  /*
   *  NAVIGATION
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldbePossibleToNavigateBetweenLoginAndRegisterWithoutError() {
    goTo(baseUrl);
    loginFluentPage.isAt();             // login page
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();      // register page
    registrationFluentPage.clickLogin();
    loginFluentPage.isAt();             // login page
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldBePossibleToBrowseToUsersFrom2Links() {
    adminLogin();

    //Users from users card in home
    homeFluentPage.clickViewUsers();
    usersFluentPage.isAt();

    goTo(homeFluentPage.getUrl());  // reset

    //Users from panel
    homeFluentPage.goToUsersPage();
    usersFluentPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void userShouldNotBeAbleToAccessHomeAfterLogout() {
    // Sign in as default appdev
    adminLogin();

    homeFluentPage.isAt();            // home page : logged
    homeFluentPage.toggleDropdown();
    homeFluentPage.logOut();          // log out
    loginFluentPage.isAt();           // redirected to login page

    goTo(homeFluentPage);             // try going back to home
    loginFluentPage.isAt();           // still on login page
  }


  // admin login, used in several tests
  @SuppressWarnings("Duplicates")
  private void adminLogin() {
    goTo(baseUrl);
    loginFluentPage.isAt();
    loginFluentPage.typeEmailAddress("admin@wp1.ch");
    loginFluentPage.typePassword("adminadmin");
    loginFluentPage.clickSignin();
    await().explicitlyFor(1000, TimeUnit.MILLISECONDS);
    homeFluentPage.isAt();
  }

  /*
   * TEST ENVIRONMENT CONFIGURATION
   */
  @Override
  public WebDriver newWebDriver(){
    String path = "./applications/chromedriver";

    if(isWindows()) { //Windows
      path += ".exe";
    }

    if (Files.exists(Paths.get(path))) {
      System.setProperty("webdriver.chrome.driver", path);
    } else {
      System.err.println("Chrome driver executable missing");
    }
    ChromeDriver chromeDriver = new ChromeDriver();
    return chromeDriver;
  }

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }

  public static boolean isWindows() {
    return (OS.contains("win"));
  }
}
