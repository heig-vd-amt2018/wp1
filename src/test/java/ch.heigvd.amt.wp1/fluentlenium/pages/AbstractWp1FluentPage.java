package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

/**
 * Most pages served by the application have the same structure: they have the
 * same header (with a navigation menu) and the same footer. These elements, and
 * the WebDriver UI locators, are capture in this abstract class. This makes it
 * possible to issue "clicks" on the navigation menu via the API from any page,
 * without code duplication.
 *
 * @author Olivier Liechti
 */
public abstract class AbstractWp1FluentPage extends FluentPage {

  private final static String linkusers = "#goUsers"; // this is the HTML id of the menu
  private final static String linkapps = "#goApps"; // this is the HTML id of the menu
  private final static String linkLogOut = "#logOut"; // this is the HTML id of the menu
  private final static String linkhome = "#goHome"; // this is the HTML id of the menu

  private final static String dropdown = "#dropdown-settings"; // this is the HTML id of the menu

  public void goToUsersPage() {
    $(linkusers).click();
  }
  public void goToAppsPage() {
    $(linkapps).click();
  }
  public void goToHome() {
    $(linkhome).click();
  }

  public void toggleDropdown() {
    $(dropdown).click();
  }

  public void logOut() {
    $(linkLogOut).click();
  }

}
