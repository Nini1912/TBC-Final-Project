package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

public class CommonPage {
    public SelenideElement acceptCookiesButton =
            $(byTagAndText("button", "თანხმობა"));

    public SelenideElement breadcrumb =
            $(".tbcx-pw-breadcrumbs__items");

}
