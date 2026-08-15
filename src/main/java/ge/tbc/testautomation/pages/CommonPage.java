package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CommonPage {
    public SelenideElement acceptCookiesButton =$x("//button[normalize-space(text())='თანხმობა']");
    public SelenideElement breadcrumb = $(".tbcx-pw-breadcrumbs__items");
    public SelenideElement surveyFrame = $("iframe[title='Feedback Survey']");
}
