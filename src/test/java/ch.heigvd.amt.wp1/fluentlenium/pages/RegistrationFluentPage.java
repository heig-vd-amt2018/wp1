package ch.heigvd.amt.wp1.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Registration" page in the app.
 */
public class RegistrationFluentPage extends AbstractWp1FluentPage {

    // Find elements by id
    private static final String inputFirstName        = "#firstname";
    private static final String inputLastName         = "#lastname";
    private static final String inputEmail            = "#email";
    private static final String inputPassword         = "#password";
    private static final String inputConfirmPassword  = "#confirmPassword";
    private static final String linkLogin             = "#login";
    private static final String buttonSignin          = "#submit";
    private static final String idPage                = "#page";

    // Input Fields
    public void typeFirstName(String firstname) {
      $(inputFirstName).fill().with(firstname);
    }
    public void typeLastName(String lastname) {
      $(inputLastName).fill().with(lastname);
    }
    public void typeEmailAddress(String email) {
      $(inputEmail).fill().with(email);
    }
    public void typePassword(String password) {
      $(inputPassword).fill().with(password);
    }
    public void typeConfirmPassword(String password) {
      $(inputConfirmPassword).fill().with(password);
    }

    // Buttons click
    public void clickSignin() {
      await().until($(buttonSignin)).present();
      $(buttonSignin).click();
    }
    public void clickLogin(){
      await().until($(linkLogin)).present();
      $(linkLogin).click();
    }


    @Override
    public void isAt() {
        await().until($(idPage)).present();
        assertThat($(idPage).first().value()).isEqualTo("registration");
    }
    public String getUrl() {
        return "/register";
    }
}

