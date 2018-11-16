package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import java.util.concurrent.TimeUnit;

/**
 * AbstractWp1FluentPage is not an actual page used in the app, but provides
 * functionnalities from UI element that are display on other pages (header, footer, ...).
 */
public abstract class AbstractWp1FluentPage extends FluentPage {

  // Find elements by id
  private final static String linkusers  = "#goUsers";
  private final static String linkapps   = "#goApps";
  private final static String linkLogOut = "#logOut";
  private final static String linkhome   = "#goHome";
  private final static String dropdown   = "#dropdown-settings";

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
    await().explicitlyFor(500, TimeUnit.MILLISECONDS);
    $(linkLogOut).click();
  }

}
