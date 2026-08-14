package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.HeaderMenuPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class NavigationSteps {
    HeaderMenuPage headerMenuPage = new HeaderMenuPage();

    public NavigationSteps navigateToForMe (){
        headerMenuPage.forMeMenu
                .shouldBe(visible)
                .click();

        return this;
    }

    public NavigationSteps navigateToCurrencyRate () {
        headerMenuPage.currencyRatesMenu
                .shouldBe(visible)
                .click();

        return this;
    }

    public NavigationSteps navigateToLoans () {
        headerMenuPage.loansMenu
                .shouldBe(visible)
                .click();

        return this;
    }

    public NavigationSteps navigateToLocations(){
        headerMenuPage.addressesMenu
                .shouldBe(visible)
                .click();

        return this;
    }

    public NavigationSteps navigateToTbcCards(){
        headerMenuPage.tbcCard
                .shouldBe(visible)
                .click();

        return this;
    }

    public NavigationSteps verifyBreadcrumbContains(String expectedText){
        headerMenuPage.breadcrumb
                .shouldBe(visible)
                .shouldHave(text(expectedText));

        return this;
    }
}
