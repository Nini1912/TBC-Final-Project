package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HeaderMenuPage extends CommonPage {
    public SelenideElement forMeMenu = $(byTagAndText("button", "ჩემთვის"));
    public SelenideElement currencyRatesMenu = $(byTagAndText("span", "ვალუტის კურსები"));
    public SelenideElement loansMenu = $(byTagAndText("span", "სესხები"));
    public SelenideElement addressesMenu = $(byTagAndText("span", "მისამართები"));
    public SelenideElement tbcCard = $(byTagAndText("span", "თიბისი ბარათი"));
}
