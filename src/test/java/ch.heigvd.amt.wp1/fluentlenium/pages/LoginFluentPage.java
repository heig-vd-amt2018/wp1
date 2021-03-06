package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Login" page in the app.
 */
public class LoginFluentPage extends FluentPage {

  // Find element by id
  private final static String inputEmail       = "#email";
  private final static String inputPassword    = "#password";
  private final static String buttonSignin     = "#submit";
  private final static String linkRegistration = "#register";
  private final static String idPage            = "#page";

  public void typeEmailAddress(String email) {
    $(inputEmail).fill().with(email);
  }

  public void typePassword(String password) {
    await().until($(inputPassword)).present();
    $(inputPassword).fill().with(password);}

  public void clickSignin() {
    await().until($(buttonSignin)).present();
    $(buttonSignin).click();
  }

  public void clickRegister(){
    await().until($(linkRegistration)).present();
    $(linkRegistration).click();
  }

  public String getUrl() {
    return "/";
  }

  @Override
  public void isAt() {
    await().until($(idPage)).present();
    assertThat($(idPage).first().value()).isEqualTo("login");
  }

}
