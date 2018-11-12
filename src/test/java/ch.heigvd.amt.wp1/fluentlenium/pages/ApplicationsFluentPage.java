package ch.heigvd.amt.wp1.fluentlenium.pages;

import ch.heigvd.amt.wp1.fluentlenium.MVCDemoFluentTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class ApplicationsFluentPage extends AbstractWp1FluentPage {

    // Find by id
    private static final String idPage          = "#page";
    private static final String createApp       = "#create-app";
    private static final String appName         = "#app-name-input";
    private static final String appDescription  = "#app-description-input";
    private static final String appDelete       = "#delete-app";
    private static final String appUpdate       = "#update-app";
    private static final String addAppBtn       = "#add-app";
    private static final String modifyApp       = "#modifyAppId";

    // Find by class
    private static final String alert       = ".alert-success";
    private static final String alertInfo   = ".alert-warning";

    // Buttons click
    public void clickAddApp() {
        $(createApp).click();
    }
    public void clickCreateAppBtn() {
        $(addAppBtn).click();
    }
    public void clickModifyApp(int id) {
        $(modifyApp + id).click();
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
        int odds = $(".odd").count();
        int evens = $(".even").count();
        assertThat(odds + evens).isEqualTo(elementNumber);
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

    public void isPreventedFromDuplicateCreation(){
        String value = $(alertInfo).first().text();
        boolean result =  value.contains("Another application has the same name. Please change.");
        assertThat(result).isEqualTo(true);
    }

    @Override
    public void isAt() { assertThat($(idPage).first().value()).isEqualTo("applications");}

    public String getUrl() {
        return MVCDemoFluentTest.baseUrl + "pages/applictions";
    }
}
