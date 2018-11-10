package ch.heigvd.amt.wp1.fluentlenium;

import ch.heigvd.amt.wp1.fluentlenium.pages.HomeFluentPage;
import ch.heigvd.amt.wp1.fluentlenium.pages.LoginFluentPage;
import ch.heigvd.amt.wp1.fluentlenium.pages.RegistrationFluentPage;
import ch.heigvd.amt.wp1.fluentlenium.pages.UsersFluentPage;
import io.probedock.client.annotations.ProbeTest;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class MVCDemoFluentTest extends FluentTest {

  private final String baseUrl = "http://localhost:8080/WP1-1.0-SNAPSHOT/";
  private static String OS = System.getProperty("os.name").toLowerCase();

  @Page
  public LoginFluentPage loginPage;

  @Page
  public RegistrationFluentPage registrationFluentPage;

  @Page
  public HomeFluentPage homePage;

  @Page
  public UsersFluentPage usersFluentPage;

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

  @Test
  @ProbeTest(tags = "WebUI")
  public void adminShouldBeCapableOfCreateAUser() {
    goTo(baseUrl);
    loginPage.isAt();
    loginPage.typeEmailAddress("admin@wp1.ch");
    loginPage.typePassword("adminadmin");
    loginPage.clickSignin();
    homePage.isAt();
    homePage.goToUsersPage();
    usersFluentPage.isAt();
    usersFluentPage.clickAddUser();
    usersFluentPage.typeFirstName("Bruce");
    usersFluentPage.typeLastName("Wayne");
    usersFluentPage.typeEmailAddress("bruce.wayne@gmail.com");
    usersFluentPage.selectRole("APPLICATION_DEVELOPER");
    usersFluentPage.clickSignin();
    usersFluentPage.isAt();
    usersFluentPage.isCreated();
  }



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

  @Override
  public WebDriver newWebDriver(){

    String path = "./applications/";

    //Windows
    if(isWindows()) {
      System.setProperty("webdriver.chrome.driver", path + "chromedriver.exe");
    }
    // Linux or Mac
    else{
      System.setProperty("webdriver.chrome.driver", path + "chromedriver");
    }
    return new ChromeDriver();
  }

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }

  public static boolean isWindows() {
    return (OS.indexOf("win") >= 0);
  }
  
}
