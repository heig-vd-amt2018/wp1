package ch.heigvd.amt.wp1.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is used to test the "Registration" page in the app.
 */
public class RegistrationFluentPage extends AbstractWp1FluentPage {

    // Find elements by id
    private final static String inputFirstName        = "#firstname";
    private final static String inputLastName         = "#lastname";
    private final static String inputEmail            = "#email";
    private final static String inputPassword         = "#password";
    private final static String inputConfirmPassword  = "#confirmPassword";
    private final static String linkLogin             = "#login";
    private final static String buttonSignin          = "#submit";
    private final static String idPage                = "#page";

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
      $(buttonSignin).click();
    }
    public void clickLogin(){
      $(linkLogin).click();
    }


    @Override
    public void isAt() {
        assertThat($(idPage).first().value()).isEqualTo("registration");
    }
    public String getUrl() {
        return "/register";
    }
}

