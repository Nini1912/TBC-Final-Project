package ge.tbc.testautomation.steps;

import com.codeborne.selenide.ClickOptions;
import ge.tbc.testautomation.pages.BurgerMenuPage;

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

    public BurgerMenuSteps verifyLinkIsVisible(String linkText){
        burgerMenuPage.getLink(linkText)
                .filterBy(visible)
                .first()
                .shouldBe(visible);

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
        burgerMenuPage.getTargetLink(pageLink)
                .shouldBe(visible)
                .click(ClickOptions.usingJavaScript());
        return this;
    }

    public BurgerMenuSteps validateTbcCardPage(){
        burgerMenuPage.headerTitle.shouldBe(visible);
        return this;
    }
}
