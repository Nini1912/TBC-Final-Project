package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;

public class LocationsPage extends CommonPage{
    public SelenideElement filter247 = $x("//label[.//span[normalize-space()='24/7']]");
    public SelenideElement filterOpen = $x("//label[.//span[normalize-space()='ღიაა']]");
    public ElementsCollection items = $$("div.tbcx-pw-atm-branches-section__list-item");
    public ElementsCollection descriptions = $$("div.tbcx-pw-atm-branches-section__list-item-description");
    public SelenideElement branchesTab = $(byTagAndText("span", "ფილიალები"));
    public SelenideElement cityDropdown =
            $(byTagAndText("div", "აირჩიე ქალაქი"))
                    .parent()
                    .parent();

    public SelenideElement citySearch = $(byAttribute("placeholder", "Filter regions"));
    public SelenideElement emptyState = $(".tbcx-dropdown-popover__empty-state");
    public SelenideElement locationSearch = $(byAttribute("placeholder", "მიუთითე სასურველი ლოკაცია"));
    public SelenideElement emptyStateTitle = $(".tbcx-pw-atm-branches-section__empty-state-title");
    public SelenideElement selectedCity = $(".tbcx-dropdown-selector__selection-text__slot__container");
    public ElementsCollection results = $$("div.tbcx-pw-atm-branches-section__list-item-title");
    public ElementsCollection skeleton = $$(".tbcx-pw-atm-branches-section__list-item-skeleton-description");

}
