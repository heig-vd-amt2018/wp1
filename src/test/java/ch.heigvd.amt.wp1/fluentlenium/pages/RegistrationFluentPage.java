package ch.heigvd.amt.wp1.fluentlenium.pages;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationFluentPage extends AbstractWp1FluentPage {

    private final static String inputFirstname = "#firstname";
    private final static String inputLastname = "#lastname";
    private final static String inputEmail= "#email";
    private final static String inputPassword= "#password";
    private final static String inputConfirmPassword= "#confirmPassword";
    private final static String linkLogin = "#login"; // id in the html code
    private final static String buttonSignin = "#submit";
    private final static String idPage = "#page";

    @Override
    public void isAt() { assertThat($(idPage).first().value()).isEqualTo("registration");}

    public void typeFirstName(String firstname) { $(inputFirstname).fill().with(firstname); }

    public void typeLastName(String lastname) { $(inputLastname).fill().with(lastname); }

    public void typeEmailAddress(String email) { $(inputEmail).fill().with(email); }

    public void typePassword(String password) { $(inputPassword).fill().with(password);}

    public void typeConfirmPassword(String password) { $(inputConfirmPassword).fill().with(password);}

    public void clickSignin() {
        $(buttonSignin).click();
    }

    public void clickLogin(){$(linkLogin).click();}

    public String getUrl() {
        return "/";
    }
}

