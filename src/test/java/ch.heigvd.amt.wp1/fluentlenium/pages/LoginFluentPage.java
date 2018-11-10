package ch.heigvd.amt.wp1.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Login" page in the MVCDemo app.
 *
 * @author Olivier Liechti
 */
public class LoginFluentPage extends AbstractMVCDemoFluentPage {

  private final static String inputEmail = "#email"; // id in the html code
  private final static String inputPassword = "#password"; // id in the html code
  private final static String buttonSignin = "#submit"; // id in the html code
  private final static String idPage = "#page"; // id in the html code


  @Override
  public void isAt() {
    assertThat($(idPage).first().value()).isEqualTo("login");
  }

  public void typeEmailAddress(String email) {
    $(inputEmail).fill().with(email);
  }

  public void typePassword(String password) { $(inputPassword).fill().with(password);}

  public void clickSignin() {
    $(buttonSignin).click();
  }

  public String getUrl() {
    return "/";
  }

}
