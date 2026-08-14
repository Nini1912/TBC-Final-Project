package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.pages.LocationsPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LocationsSearchSteps {
    LocationsPage locationsPage = new LocationsPage();

    public LocationsSearchSteps selectBranchesTab(){
        locationsPage.branchesTab
                .shouldBe(visible)
                .click();
        return this;
    }

    public LocationsSearchSteps validateTabIsActive(String errorMessage){
        SelenideElement tabParent =
                locationsPage.branchesTab.parent();

        JavascriptExecutor js =
                (JavascriptExecutor) WebDriverRunner.getWebDriver();

        String afterBorderColor =
                (String) js.executeScript(
                        "return window.getComputedStyle(arguments[0], '::after')" +
                                ".getPropertyValue('background-color');",
                        tabParent
                );

        Assert.assertTrue(
                afterBorderColor.contains("0, 173, 238"),
                errorMessage +
                        "Underline color: " + afterBorderColor
        );
        return this;
    }

    public LocationsSearchSteps setValueToCityInput(String searchValue){
        locationsPage.cityDropdown
                .shouldBe(visible)
                .click();

        locationsPage.citySearch.shouldBe(visible);

        locationsPage.citySearch
                .setValue(searchValue);

        return this;
    }

    public LocationsSearchSteps selectCityFromDropdown(String city){
        locationsPage.cityDropdown
                .shouldBe(visible)
                .click();

        $(byText(city))
                .shouldBe(visible)
                .click();

        return this;
    }

    public LocationsSearchSteps validateCityNotFound(String message){
        locationsPage.emptyState
                .shouldBe(visible)
                .shouldHave(text(message));
        return this;
    }

    public LocationsSearchSteps validateCityFound(String city){
        locationsPage.selectedCity
                .shouldBe(visible)
                .shouldHave(text(city));
        return this;
    }

    public LocationsSearchSteps setValueToLocationInput (String location){
        locationsPage.locationSearch
                .shouldBe(visible)
                .setValue(location);

        locationsPage.locationSearch
                .shouldHave(value(location));

        return this;
    }

    public LocationsSearchSteps validateLocationNotFound(String message){
        locationsPage.emptyStateTitle
                .shouldBe(visible)
                .shouldHave(text(message));

        return this;
    }

    public LocationsSearchSteps validateLocationFound(String location){
        locationsPage.skeleton.shouldBe(CollectionCondition.empty);

        for (SelenideElement result : locationsPage.results) {
            result.shouldHave(text(location));
        }

        return this;
    }
}
