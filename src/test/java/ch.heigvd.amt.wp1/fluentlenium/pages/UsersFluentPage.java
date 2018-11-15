package ch.heigvd.amt.wp1.fluentlenium.pages;

import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.core.FluentPage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;

/**
 * This class is used to test the "Users" page in the app.
 */
public class UsersFluentPage extends AbstractWp1FluentPage {

  // Find elements by id
  private static final String linkUser          = "#addUser";
  private static final String addUserBtn        = "#submit";
  private static final String idPage            = "#page";
  private static final String inputEmail        = "#email";
  private static final String inputRole         = "#role";
  private static final String inputFirstname    = "#firstname";
  private static final String inputLastname     = "#lastname";
  private static final String modifyUserAction  = "#modifyUser";
  private static final String updateUserbtn     = "#user-update-btn";
  private static final String deleteUserbtn     = "#user-delete-btn";
  private static final String resetUserbtn      = "#user-reset-btn";
  private static final String usersTable        = "#users";

  // Find elements by class
  private static final String entryDropdown = ".input-sm";
  private static final String alert         = ".alert-success";
  private static final String alertInfo     = ".alert-warning";
  private static final String alertDanger   = ".alert-danger";

  // Buttons click
  public void clickAddUser() {
    $(linkUser).click();
  }
  public void clickAdd() {
    $(addUserBtn).click();
  }
  public void clickModifyUser(int index) {
    $(modifyUserAction).get(index).click();
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

  // Input Fields
  public void typeFirstName(String firstName) {
    $(inputFirstname).fill().with(firstName);
  }
  public void typeLastName(String lastName) {
    $(inputLastname).fill().with(lastName);
  }
  public void typeEmailAddress(String email) {
    $(inputEmail).fill().with(email);
  }
  public void selectRole(String role){
    $(inputRole).fillSelect().withValue(role);
  }

  // Assertions
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
  public void isNotInTable(String stringField) {
    // expand list to show 100 users
    $(entryDropdown).first().fillSelect().withValue("100");

    // try finding stringField
    String table = $(usersTable).first().text();
    boolean result = table.contains(stringField);
    assertThat(result).isEqualTo(false);
  }
  public void isOnceInTable(String stringField) {
    // expand list to show 100 users
    $(entryDropdown).first().fillSelect().withValue("100");

    // count occurrence
    String table = $(usersTable).first().text();
    int count = StringUtils.countMatches(table, stringField);
    assertThat(count).isEqualTo(1);
  }
  public void isPreventedFromDuplicateCreation() {
    String value = $(alertInfo).first().text();
    boolean result =  value.contains("This email address already exist.");
    assertThat(result).isEqualTo(true);
  }
  public void isReset() {
    String value = $(alert).first().text();
    boolean result =  value.contains("Password reset, email sent.");
    assertThat(result).isEqualTo(true);
  }
  public void isPreventedFromResettingTwice() {
    String value = $(alertDanger).first().text();
    boolean result =  value.contains("Password already reset.");
    assertThat(result).isEqualTo(true);
  }

  // helpers
  @Override
  public void isAt() {
    assertThat($(idPage).first().value()).isEqualTo("users");
  }
  public String getUrl() { return "pages/users"; }
}