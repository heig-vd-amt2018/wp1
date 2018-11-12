
package ch.heigvd.amt.wp1.fluentlenium.pages;

import ch.heigvd.amt.wp1.fluentlenium.MVCDemoFluentTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Home" page in the MVCDemo app. Notice that in
 * the constructor, we check if we are on the correct page by checking the HTML
 * title of the page. This is used to detect navigation issues (for example, you
 * expect to arrive on the Beers page, but the title of the actual page is
 * "Login Page" because of some error.
 *
 * @author Olivier Liechti
 */
public class HomeFluentPage extends AbstractWp1FluentPage {

  private final static String idPage = "#page";               // id in the html code
  private final static String fieldViewUsers = "#users-page"; // id in the html code
  private final static String alert = "#alert"; // id in the html code
  private static final String appNumber = ".huge";

  @Override
  public void isAt() { assertThat($(idPage).first().value()).isEqualTo("home");}

  public void clickViewUsers() {
      $(fieldViewUsers).click();
  }

  public void isRedirectedOnRightsError() {
      String value = $(alert).first().text();
      boolean result =  value.contains("You do not have the rights to access this page.");
      assertThat(result).isEqualTo(true);
  }

  public void amountOfAppsMatches(int appsAmount) {
      String value = $(appNumber).first().text();
      assertThat(value).isEqualTo(appsAmount + "");
  }

  public String getUrl() {
    return MVCDemoFluentTest.baseUrl + "pages/home";
  }

}
