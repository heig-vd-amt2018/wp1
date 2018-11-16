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
 *  Test scenario of a App developer registering and creating applications <br>
 *  Elements tests : <br>
 *  - Registration<br>
 *  - App creation<br>
 *  - App browsing in the tabe <br>
 *  - Log out
 */
public class Wp1FluentScenario1Test extends FluentTest {

  public static final String baseUrl = "http://localhost:8080/WP1-1.0-SNAPSHOT/";
  private static String OS = System.getProperty("os.name").toLowerCase();

  @Page
  public LoginFluentPage loginFluentPage;
  @Page
  public RegistrationFluentPage registrationFluentPage;
  @Page
  public HomeFluentPage homeFluentPage;
  @Page
  public ApplicationsFluentPage appsFluentPage;

  /*
   *  BIG TEST CASE SCENARIO
   */
  @Test
  @ProbeTest(tags = "WebUI")
  public void devAppShouldRegisterCreateAppsBrowseAndLogOut () {
    goTo(baseUrl);      // login page

    loginFluentPage.isAt();

    // Register
    loginFluentPage.clickRegister();
    registrationFluentPage.isAt();
    registrationFluentPage.typeFirstName("Peter");
    registrationFluentPage.typeLastName("Parker");
    registrationFluentPage.typeEmailAddress("peter.parker@dailybugle.com");
    registrationFluentPage.typePassword("spiderman");
    registrationFluentPage.typeConfirmPassword("spiderman");
    registrationFluentPage.clickSignin();
    homeFluentPage.isAt();      // registration OK => home page

    // Go to Applications and create apps
    homeFluentPage.goToAppsPage();
    for (int i = 1; i <= 25; i++) {
      appsFluentPage.clickAddApp();
      appsFluentPage.typeAppName("City " + i);
      appsFluentPage.typeAppDescription("People saved in City " + i);
      appsFluentPage.clickCreateAppBtn();
      await().explicitlyFor(100);
      appsFluentPage.isCreated();   // app creation OK
    }

    // Browse
    appsFluentPage.clickNext();
    await().untilPage().isLoaded();
    appsFluentPage.containsNElements(10); // amount of apps/page OK
    await().untilPage().isLoaded();
    appsFluentPage.clickNext();
    appsFluentPage.containsNElements(10); // amount of apps/page OK
    await().untilPage().isLoaded();
    appsFluentPage.clickNext();
    appsFluentPage.containsNElements(5); // amount of apps/page OK

    // Go back to home
    appsFluentPage.goToHome();
    await().untilPage().isLoaded();
    homeFluentPage.isAt();        // returned to home OK
    homeFluentPage.amountOfAppsMatches(25);       // total amount of apps OK

    // Logs out
    homeFluentPage.toggleDropdown();
    homeFluentPage.logOut();
    await().untilPage().isLoaded();
    loginFluentPage.isAt();       // redirected to login OK

    // Try to go to home
    goTo(homeFluentPage);
    await().untilPage().isLoaded();
    loginFluentPage.isAt();       // page still login OK
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
