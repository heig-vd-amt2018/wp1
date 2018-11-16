package ch.heigvd.amt.wp1.fluentlenium.pages;

import ch.heigvd.amt.wp1.fluentlenium.Wp1FluentTest;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

/**
 * This class is used to test the "Applications" page in the app.
 */
public class ApplicationsFluentPage extends AbstractWp1FluentPage {

    // Find elements by id
    private static final String idPage          = "#page";
    private static final String createApp       = "#create-app";
    private static final String appName         = "#app-name-input";
    private static final String appDescription  = "#app-description-input";
    private static final String appDelete       = "#delete-app";
    private static final String appUpdate       = "#update-app";
    private static final String addAppBtn       = "#add-app";
    private static final String modifyApp       = "#modifyApp";
    private static final String appsTable       = "#applications";

    // Find elements by class
    private static final String alert         = ".alert-success";
    private static final String alertInfo     = ".alert-warning";
    private static final String entryDropdown = ".input-sm";

    // Buttons click
    public void clickAddApp() {
        await().explicitlyFor(500, TimeUnit.MILLISECONDS);
        $(createApp).click();
    }
    public void clickCreateAppBtn() {
        $(addAppBtn).click();
    }
    public void clickModifyApp(int index) {
        $(modifyApp).get(index).click();
    }
    public void clickSave() {
        $(appUpdate).click();
    }
    public void clickNext() {
        $("a", withText("Next")).click();
    }
    public void clickDelete() {
        $(appDelete).click();
    }

    // Input Fields
    public void typeAppName(String name) {
        $(appName).fill().with(name);
    }
    public void typeAppDescription(String description) {
        $(appDescription).fill().with(description);
    }

    // Assertions
    public void containsNElements(int elementNumber) {
        assertThat($(modifyApp).count()).isEqualTo(elementNumber);
    }
    public void isCreated(){
        String value = $(alert).first().text();
        boolean result =  value.contains("Application has been successfully created.");
        assertThat(result).isEqualTo(true);
    }
    public void isModified(){
        String value = $(alert).first().text();
        boolean result =  value.contains("Application has been successfully updated.");
        assertThat(result).isEqualTo(true);
    }
    public void isDeleted() {
        String value = $(alert).first().text();
        boolean result =  value.contains("Application has been successfully deleted.");
        assertThat(result).isEqualTo(true);
    }
    public void isPreventedFromDuplicateCreation(){
        String value = $(alertInfo).first().text();
        boolean result =  value.contains("Another application has the same name. Please change.");
        assertThat(result).isEqualTo(true);
    }
    public void isNotInTable(String stringField) {
        // expand list to show 100 users
        $(entryDropdown).first().fillSelect().withValue("100");

        // try finding stringField
        String table = $(appsTable).first().text();
        boolean result = table.contains(stringField);
        assertThat(result).isEqualTo(false);
    }
    public void isOnceInTable(String stringField) {
        // expand list to show 100 users
        $(entryDropdown).first().fillSelect().withValue("100");

        // count occurrence
        String table = $(appsTable).first().text();
        int count = StringUtils.countMatches(table, stringField);
        assertThat(count).isEqualTo(1);
    }

    // helpers
    public int countElemsInTable() {
        return $(modifyApp).count();
    }

    @Override
    public void isAt() { assertThat($(idPage).first().value()).isEqualTo("applications");}

    public String getUrl() {
        return Wp1FluentTest.baseUrl + "pages/applictions";
    }
}
