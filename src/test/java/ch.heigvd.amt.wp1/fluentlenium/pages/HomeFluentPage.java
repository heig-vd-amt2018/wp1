
package ch.heigvd.amt.wp1.fluentlenium.pages;

import ch.heigvd.amt.wp1.fluentlenium.Wp1FluentTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Home" page in the app.
 */
public class HomeFluentPage extends AbstractWp1FluentPage {

  // Find elements by id
  private static final String idPage          = "#page";
  private static final String fieldViewUsers  = "#users-page";
  private static final String alert           = "#alert";

  private static final String appNumber       = ".huge"; // Find elements by class

  public void clickViewUsers() {
      $(fieldViewUsers).click();
  }

  public void isRedirectedOnRightsError() {
      await().until($(alert)).present();
      String value = $(alert).first().text();
      boolean result =  value.contains("You do not have the rights to access this page.");
      assertThat(result).isEqualTo(true);
  }

  public void amountOfAppsMatches(int appsAmount) {
      await().until($(appNumber)).present();
      String value = $(appNumber).first().text();
      assertThat(value).isEqualTo(appsAmount + "");
  }

  public String getUrl() {
    return Wp1FluentTest.baseUrl + "pages/home";
  }

  @Override
  public void isAt() {
    await().until($(idPage)).present();
    assertThat($(idPage).first().value()).isEqualTo("home");
  }

}
