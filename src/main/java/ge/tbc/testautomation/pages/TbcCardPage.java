package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

public class TbcCardPage {
    public SelenideElement getCardButton = $(byTagAndText( "button","აიღე ბარათი"));
    public SelenideElement popupBanner = $(".tbcx-pw-app-download-banner-popup__qr");
}
