package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;

public class UsersFluentPage extends FluentPage {

    private final static String linkUser = "#addUser";
    private final static String addUserBtn = "#submit";
    private final static String idPage = "#page";
    private final static String inputEmail = "#email";
    private final static String inputRole = "#role";
    private final static String inputFirstname = "#firstname";
    private final static String inputLastname = "#lastname";
    private final static String alert = ".alert-success";
    private final static String alertInfo = ".alert-warning";
    private final static String alertDanger = ".alert-danger";
    private final static String modifyUserAction = "#modifyAt";
    private final static String updateUserbtn = "#user-update-btn";
    private final static String deleteUserbtn = "#user-delete-btn";
    private final static String resetUserbtn = "#user-reset-btn";
    private final static String usersTable = "#users";

    @Override
    public void isAt() {
        assertThat($(idPage).first().value()).isEqualTo("users");
    }

    public void clickAddUser() { $(linkUser).click();}

    public void clickAdd() {
        $(addUserBtn).click();
    }

    public void clickModifiyUser(int id) {
        $(modifyUserAction + id).click();
    }

    public void clickUpdate() {
        $(updateUserbtn).click();
    }

    public void clickDelete() {
        $(deleteUserbtn).click();
    }

    public void clickReset() {
        $(resetUserbtn).click();
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

    public void isUpdated(){
        String value = $(alert).first().text();
        boolean result =  value.contains("User has been successfully updated.");
        assertThat(result).isEqualTo(true);
    }

    public void isDeleted() {
        String value = $(alert).first().text();
        boolean result =  value.contains("User has been successfully deleted.");
        assertThat(result).isEqualTo(true);
    }

    public void notAllFilledError(){
        String value = $(alertDanger).first().text();
        boolean result =  value.contains("All field should be filled.");
        assertThat(result).isEqualTo(true);
    }

    public void isNotInTable(String userFirstName) {
        String table = $(usersTable).first().text();
        boolean result = table.contains(userFirstName);
        assertThat(result).isEqualTo(false);
    }

    public String getUrl() { return "pages/users"; }

    public void isPreventedFromDuplicateCreation() {
        String value = $(alertInfo).first().text();
        boolean result =  value.contains("This email address already exist.");
        assertThat(result).isEqualTo(true);
    }
}