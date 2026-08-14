package ge.tbc.testautomation.steps;

import com.codeborne.selenide.ClickOptions;
import ge.tbc.testautomation.pages.BurgerMenuPage;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BurgerMenuSteps {
    BurgerMenuPage burgerMenuPage = new BurgerMenuPage();

    public BurgerMenuSteps openBurgerMenu(){
        burgerMenuPage.burgerMenu.shouldBe(visible)
                .click();

        burgerMenuPage.megaMenuContainer
                .shouldBe(visible);
        return this;
    }

    public BurgerMenuSteps verifyLinks (){
        List<String> expectedLinks = List.of(
                "ჩემთვის",
                "სესხები",
                "ბარათები",
                "ანაბრები",
                "ციფრული სერვისები",
                "სხვა პროდუქტები",
                "კონცეპტი",
                "ახალი თაობისთვის",
                "ემიგრანტებისთვის",
                "ექსპატებისთვის",
                "ვალუტის კურსები",
                "შეთავაზებები",
                "ერთგულება",
                "მისამართები"
        );

        for (String linkText : expectedLinks) {
            $$x("//tbcx-pw-mega-menu//*[normalize-space()='" + linkText + "']")
                    .filterBy(visible)
                    .first()
                    .shouldBe(visible);
        }

        return this;
    }

    public BurgerMenuSteps navigateToPage(String linkText){
        burgerMenuPage.getLink(linkText)
                .filterBy(visible)
                .first()
                .shouldBe(visible)
                .click();
        return this;
    }

    public BurgerMenuSteps validatePage(String text){
        burgerMenuPage.textBox
                .shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    public BurgerMenuSteps validateLocationsPage(String text){
        $(byText(text)).shouldBe(visible);
        return this;
    }

    public BurgerMenuSteps navigateToSubPage(String pageLink){
        $(".tbc-accordion.tbc-accordion--custom.tbc-accordion--expanded")
                .$(String.format("a[href='%s']", pageLink))
                .shouldBe(visible)
                .click(ClickOptions.usingJavaScript());
        return this;
    }

    public BurgerMenuSteps validateTbcCardPage(){
        $("h1 .ng-star-inserted").shouldBe(visible);
        return this;
    }
}
