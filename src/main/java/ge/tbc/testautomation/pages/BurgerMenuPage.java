package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class BurgerMenuPage {
    public SelenideElement burgerMenu = $x("//*[contains(@style, 'burger-menu')] | //button[contains(@class, 'burger')] | //*[contains(@class, 'header__burger')]");
    public SelenideElement megaMenuContainer = $("tbcx-pw-mega-menu");
    public ElementsCollection getLink(String linkText){
        return $$x(String.format("//tbcx-pw-mega-menu//*[normalize-space()='%s']", linkText));
    }
    public SelenideElement textBox = $(".tbcx-pw-popular-currencies__main-title");
    public SelenideElement getTargetLink(String pageLink){
        return $(".tbc-accordion.tbc-accordion--custom.tbc-accordion--expanded")
            .$("a[href='" + pageLink + "']");
    }
    public SelenideElement headerTitle = $("h1 .ng-star-inserted");
}
