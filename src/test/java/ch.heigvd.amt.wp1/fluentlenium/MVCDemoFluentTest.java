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

/**
 *  Requires predifined users {admin@wp1.ch, adminadmin} and
 *  {user1@wp1.ch, adminadmin} defined in src/main/resources/META-INF/sql/data.sql
 */
public class MVCDemoFluentTest extends FluentTest {

  public static final String baseUrl = "http://localhost:8080/WP1-1.0-SNAPSHOT/";
  private static String OS = System.getProperty("os.name").toLowerCase();

  @Page
  public LoginFluentPage loginPage;

  @Page
  public RegistrationFluentPage registrationFluentPage;

  @Page
  public HomeFluentPage homePage;

  @Page
  public UsersFluentPage usersFluentPage;

  @Page
  public ApplicationsFluentPage appsFluentPage;

  /*
   *  TEST CASE 1
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void devAppShouldRegisterCreateAppsAndBrowse () {
      goTo(baseUrl);
      loginPage.isAt();

      // Register
      loginPage.clickRegister();
      registrationFluentPage.typeFirstName("Peter");
      registrationFluentPage.typeLastName("Parker");
      registrationFluentPage.typeEmailAddress("peter.parker@dailybugle.com");
      registrationFluentPage.typePassword("spiderman");
      registrationFluentPage.typeConfirmPassword("spiderman");
      registrationFluentPage.clickSignin();
      homePage.isAt();      // registration OK

      // Go to Applications and create apps
      homePage.goToAppsPage();
      for (int i = 1; i <= 25; i++) {
          appsFluentPage.clickAddApp();
          appsFluentPage.typeAppName("City " + i);
          appsFluentPage.typeAppDescription("People saved in City " + i);
          appsFluentPage.clickCreateAppBtn();
          await().explicitlyFor(100);
          appsFluentPage.isCreated();   // app creation OK
      }

      // Browse
      await().explicitlyFor(500);
      appsFluentPage.clickNext();
      appsFluentPage.containsNElements(10); // amount of apps/page OK
      await().explicitlyFor(500);
      appsFluentPage.clickNext();
      appsFluentPage.containsNElements(10); // amount of apps/page OK
      await().explicitlyFor(500);
      appsFluentPage.clickNext();
      appsFluentPage.containsNElements(5); // amount of apps/page OK

      // Go back to home
      appsFluentPage.goToHome();
      homePage.isAt();      // returned to home OK
      homePage.amountOfAppsMatches(25);
  }

  /*
   *  REGISTRATION
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToSigninWithAnInvalidEmail() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("not a valid email");
    loginPage.typePassword("any password");
    loginPage.clickSignin();
    loginPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void successfulSigninShouldBringUserToHomePage() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void successfulRegistrationShouldBringUserToHomePage() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("Jean");
    registrationFluentPage.typeLastName("Paul");
    registrationFluentPage.typeEmailAddress("jeanpaul@test.ch");
    registrationFluentPage.typePassword("test");
    registrationFluentPage.typeConfirmPassword("test");
    registrationFluentPage.clickSignin();
    homePage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithoutInformation(){
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithTwoDifferentPassword(){
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("Richard");
    registrationFluentPage.typeLastName("Aldana");
    registrationFluentPage.typeEmailAddress("Richard.Aldana@gmail.com");
    registrationFluentPage.typePassword("password");
    registrationFluentPage.typeConfirmPassword("wrong");
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldNotBePossibleToRegisterWithAnExistingEmail() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("James");
    registrationFluentPage.typeLastName("Bond");
    registrationFluentPage.typeEmailAddress("user1@wp1.ch"); //Email that is created at each initialization
    registrationFluentPage.typePassword("password");
    registrationFluentPage.typeConfirmPassword("password");
    registrationFluentPage.clickSignin();
    registrationFluentPage.isAt();
  }


  /*
   *  ADMIN ACTIONS
   */

  // USER MANAGEMENT
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToCreateAUser() {
    // admin login
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();
    homePage.goToUsersPage();

    // Add user
    usersFluentPage.isAt();
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName("Bruce");
    usersFluentPage.typeLastName("Wayne");
    usersFluentPage.typeEmailAddress("bruce.wayne@gmail.com");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    usersFluentPage.isCreated();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateAUserWithoutEmail() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    // try creating empty user
    final String FIRST_NAME = "FirstName";
    homePage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName(FIRST_NAME);
    usersFluentPage.typeLastName("LastName");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    usersFluentPage.isAt();
    usersFluentPage.isNotInTable(FIRST_NAME);
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateDuplicateUser() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    // try creating existing user
    homePage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName("je");
    usersFluentPage.typeLastName("I");
    usersFluentPage.typeEmailAddress("user1@wp1.ch");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickAdd();
    await().explicitlyFor(500);
    usersFluentPage.isAt();
    usersFluentPage.isPreventedFromDuplicateCreation();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToModifyUser() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    homePage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickModifiyUser(2);
    usersFluentPage.typeLastName("I");
    usersFluentPage.clickUpdate();
    usersFluentPage.isUpdated();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToModifyUserWithEmptyEmail() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    homePage.goToUsersPage();
    usersFluentPage.isAt();
    await().explicitlyFor(500);
    usersFluentPage.clickModifiyUser(2);
    usersFluentPage.typeEmailAddress("");
    usersFluentPage.clickUpdate();
    usersFluentPage.notAllFilledError();     // WARNING : form validation on FireFox, without alert-danger !
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToResetUser() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    homePage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickModifiyUser(3);
    usersFluentPage.clickReset();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToDeleteUser() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    homePage.goToUsersPage();
    usersFluentPage.isAt();
    await().explicitlyFor(1000);             //TODO wait for table
    usersFluentPage.clickModifiyUser(2);
    usersFluentPage.clickDelete();
    usersFluentPage.isDeleted();
  }

  // APPLICATION MANAGEMENT
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToCreateApplication() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    homePage.goToAppsPage();
    appsFluentPage.isAt();
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName("Test app");
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();
  }

  // APPLICATION MANAGEMENT
  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldNotBeAbleToCreateDuplicateApplication() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    // TODO use a default app
    homePage.goToAppsPage();
    appsFluentPage.isAt();
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName("Test application 1");
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();

    // duplicate app
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName("Test application 1");
    appsFluentPage.typeAppDescription("My best app");
    appsFluentPage.clickCreateAppBtn();
    await().explicitlyFor(500);                         //TODO better wait?
    appsFluentPage.isPreventedFromDuplicateCreation();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeAbleToModifyApplication() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    // TODO use a default app
    homePage.goToAppsPage();
    appsFluentPage.isAt();
    appsFluentPage.clickAddApp();
    appsFluentPage.typeAppName("First App");
    appsFluentPage.typeAppDescription("The first app");
    appsFluentPage.clickCreateAppBtn();
    appsFluentPage.isCreated();

    appsFluentPage.isAt();
    appsFluentPage.clickModifyApp(1);
    appsFluentPage.typeAppName("My very first App");
    appsFluentPage.clickSave();
    appsFluentPage.isModified();
  }


  /*
   *  APP_DEVELOPPER ACTIONS
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void appDevelopperShouldNotSeeUsers() {
    // Sign in as default appdev
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("user1@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    goTo(usersFluentPage.getUrl());
    homePage.isAt();
    homePage.isRedirectedOnRightsError();
  }


  /*
   *  NAVIGATION
   */

  @Test
  @ProbeTest(tags = "WebUI")
  public void itShouldbePossibleToNavigateBetweenLoginAndRegisterWithoutError() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.clickLogin();
    loginPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void usersFromPanelAndFromHomeCardShouldHeadToTheSamePage() {
    // Sign in as default admin
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();

    //Users from users card in home
    homePage.clickViewUsers();
    usersFluentPage.isAt();

    goTo(homePage.getUrl());  // reset

    //Users from panel
    homePage.goToUsersPage();
    usersFluentPage.isAt();
  }

  @Test
  @ProbeTest(tags = "WebUI")
  public void userShouldNotBeAbleToAccessHomeAfterLogout() {
    // Sign in as default appdev
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("user1@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();
    homePage.toggleDropdown();
    homePage.logOut();

    goTo(homePage);
    loginPage.isAt();
    //TODO fix logout
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
    return new ChromeDriver();
  }

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }

  public static boolean isWindows() {
    return (OS.contains("win"));
  }
  
}
