package ch.heigvd.amt.wp1.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Profile" page in the app.
 */
public class ProfileFluentPage extends AbstractWp1FluentPage{

  // Find elements by id
  private static final String idPage                = "#page";
  private final static String inputPassword         = "#password";
  private final static String inputConfirmPassword  = "#confirmPassword";
  private final static String buttonSave            = "#save";

  // Find elements by class
  private static final String alertInfo     = ".alert-warning";
  private static final String alertSuccess  = ".alert-success";
  private static final String alertDanger   = ".alert-danger";

  public void clickSave() { // Button click
    $(buttonSave).first().click();
  }

  // Input Fields
  public void typePassword(String password) {
    $(inputPassword).fill().with(password);
  }
  public void typeConfirmPassword(String password) {
    $(inputConfirmPassword).fill().with(password);
  }

  // Assertions
  public void isRedirected() {
    String value = $(alertInfo).first().text();
    boolean result =  value.contains("You must reset your password.");
    assertThat(result).isEqualTo(true);
  }
  public void isPwdChanged() {
    String value = $(alertSuccess).first().text();
    boolean result =  value.contains("Profile has been successfully updated.");
    assertThat(result).isEqualTo(true);
  }
  public void isPwdEmpty() {
    String value = $(alertDanger).first().text();
    boolean result =  value.contains("Password can't be empty.");
    assertThat(result).isEqualTo(true);
  }

  @Override
  public void isAt() {
    assertThat($(idPage).first().value()).isEqualTo("profile");
  }
}
