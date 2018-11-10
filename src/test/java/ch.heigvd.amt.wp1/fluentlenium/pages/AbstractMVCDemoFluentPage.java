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
public abstract class AbstractMVCDemoFluentPage extends FluentPage {

  private final static String linkusers = "#goUsers"; // this is the HTML id of the menu

  public void goToUsersPage() {$(linkusers).click();}

}
