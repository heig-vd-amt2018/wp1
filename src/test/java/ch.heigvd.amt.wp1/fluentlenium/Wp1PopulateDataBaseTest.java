package ch.heigvd.amt.wp1.fluentlenium;

import ch.heigvd.amt.wp1.fluentlenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 *  DB population using UI tests with Fluentlenium<br>
 *  Workd with Chrome driver executable<br><br>
 *
 *  Requires default users {admin@wp1.ch, adminadmin} and
 *  {user1@wp1.ch, adminadmin, ENABLED}, {user2@wp1.ch, adminadmin, ENABLED}, {user3@wp1.ch, adminadmin, RESET}
 *  defined in src/main/resources/META-INF/sql/data.sql <br>
 */
public class Wp1PopulateDataBaseTest extends FluentTest {

  public static final String baseUrl = "http://localhost:8080/WP1-1.0-SNAPSHOT/";
  private static String OS = System.getProperty("os.name").toLowerCase();

  @Page
  public LoginFluentPage loginFluentPage;
  @Page
  public HomeFluentPage homeFluentPage;
  @Page
  public ApplicationsFluentPage appsFluentPage;

  @Test
  @ProbeTest(tags = "populateDB")
  public void defaultAdminShouldBeAbleToCreate2Apps(){
    createApps("admin@wp1.ch", "adminadmin", 2);
  }

  @Test
  @ProbeTest(tags = "populateDB")
  public void defaultUser1ShouldBeAbleToCreate25Apps(){
    createApps("user1@wp1.ch", "adminadmin", 25);
  }

  @Test
  @ProbeTest(tags = "populateDB")
  public void defaultUser2ShouldBeAbleToCreate25Apps(){
    createApps("user2@wp1.ch", "adminadmin", 25);
  }

  /**
   * Generic app creation for default users
   * @param amount number of applications to create
   */
  private void createApps(String userEmail, String password, int amount) {
    String user = userEmail.split("@")[0];
    goTo(baseUrl);      // login page
    loginFluentPage.isAt();

    // Login
    loginFluentPage.typeEmailAddress(userEmail);
    loginFluentPage.typePassword(password);
    loginFluentPage.clickSignin();
    homeFluentPage.isAt();      // login OK => home page

    // Create apps
    homeFluentPage.goToAppsPage();
    for (int i = 1; i <= amount; i++) {
      appsFluentPage.clickAddApp();
      appsFluentPage.typeAppName(user + "App" + i);
      appsFluentPage.typeAppDescription(user + " app " + i);
      appsFluentPage.clickCreateAppBtn();
      appsFluentPage.isCreated();   // app creation OK
    }
    appsFluentPage.toggleDropdown();
    appsFluentPage.logOut();
    loginFluentPage.isAt();     // logged out OK
  }

  /*
   * TEST ENVIRONMENT CONFIGURATION
   */
  @Override
  public String getWebDriver(){
    return "chrome";
  }

  @Override
  public WebDriver newWebDriver() {
    String path = "./applications/chromedriver";

    if(isWindows()) { //Windows
      path += ".exe";
    }

    if (Files.exists(Paths.get(path))) {
      System.setProperty("webdriver.chrome.driver", path);
    } else {
      System.err.println("Chrome driver executable missing");
    }
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments("--disable-gpu");

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("chromeOptions", options);

    ChromeDriver chromeDriver = new ChromeDriver(capabilities);
    chromeDriver.manage().timeouts().pageLoadTimeout(500, TimeUnit.MILLISECONDS);

    return new ChromeDriver(capabilities);
  }

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }
  public static boolean isWindows() {
    return (OS.contains("win"));
  }
}
