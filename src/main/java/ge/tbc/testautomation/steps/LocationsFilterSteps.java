package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.LocationsPage;
import ge.tbc.testautomation.util.IsOpenCheck;
import org.testng.Assert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;

public class LocationsFilterSteps {
    LocationsPage locationsPage = new LocationsPage();
    IsOpenCheck isOpenCheck = new IsOpenCheck();

    public LocationsFilterSteps append247Filter(){
        locationsPage.filter247
                .shouldBe(visible)
                .click();

        locationsPage.filter247.$("input.tbcx-pw-chip__input")
                .shouldBe(selected);
        return this;
    }

    public LocationsFilterSteps appendOpenFilter(){
        locationsPage.filterOpen
                .shouldBe(visible)
                .click();

        locationsPage.filterOpen.$("input.tbcx-pw-chip__input")
                .shouldBe(selected);

        return this;
    }

    public LocationsFilterSteps validateFilterIsAppended(){
        locationsPage.items.first().shouldBe(visible);.
        return this;
    }

    public LocationsFilterSteps validate247FilterWorks(String filter){
        locationsPage.skeleton.shouldBe(CollectionCondition.empty);
        locationsPage.descriptions.shouldHave(sizeGreaterThan(0));
        locationsPage.descriptions.forEach(el -> el.shouldHave(text(filter)));
        return this;
    }

    public LocationsFilterSteps validateOpenFilterWorks(){
        locationsPage.items.shouldHave(sizeGreaterThan(0));

        locationsPage.descriptions.shouldBe(CollectionCondition.empty);

        locationsPage.descriptions.shouldHave(sizeGreaterThan(0));

        for (SelenideElement description : locationsPage.descriptions) {
            String scheduleText = description.getText();
            boolean isValid = isOpenCheck.isCurrentlyOpen(scheduleText);
            Assert.assertTrue(isValid);
        }
        return this;
    }

    public LocationsFilterSteps resetFilters(){
        if (locationsPage.filter247.$("input.tbcx-pw-chip__input").is(selected)) {
            locationsPage.filter247.click();
        }
        if (locationsPage.filterOpen.$("input.tbcx-pw-chip__input").is(selected)) {
            locationsPage.filterOpen.click();
        }
        return this;
    }
}
