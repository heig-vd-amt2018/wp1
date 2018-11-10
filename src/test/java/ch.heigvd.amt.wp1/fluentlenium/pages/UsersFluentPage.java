package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;

public class UsersFluentPage extends FluentPage {

    private final static String linkUser = "#addUser";
    private final static String buttonSignin = "#submit";
    private final static String idPage = "#page";
    private final static String inputEmail = "#email";
    private final static String inputRole = "#role";
    private final static String inputFirstname = "#firstname";
    private final static String inputLastname = "#lastname";
    private final static String alert = ".alert-success";

    @Override
    public void isAt() {
        assertThat($(idPage).first().value()).isEqualTo("users");
    }

    public void clickAddUser() { $(linkUser).click();}

    public void clickSignin() {
        $(buttonSignin).click();
    }

    public void typeFirstName(String firstname) { $(inputFirstname).fill().with(firstname); }

    public void typeLastName(String lastname) { $(inputLastname).fill().with(lastname); }

    public void typeEmailAddress(String email) { $(inputEmail).fill().with(email); }

    public void selectRole(String role){ $(inputRole).fillSelect().withValue(role);}

    public void isCreated(){

        String value = $(alert).first().text();
        boolean result =  value.contains("User created.");
        assertThat(result).isEqualTo(true);
    }

    public String getUrl() { return "/"; }
}