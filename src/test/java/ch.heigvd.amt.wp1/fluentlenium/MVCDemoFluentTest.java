package ch.heigvd.amt.wp1.fluentlenium;

import ch.heigvd.amt.wp1.fluentlenium.pages.HomeFluentPage;
import ch.heigvd.amt.wp1.fluentlenium.pages.LoginFluentPage;
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
  public HomeFluentPage homePage;

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
