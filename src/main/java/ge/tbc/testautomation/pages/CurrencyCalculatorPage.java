package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CurrencyCalculatorPage extends CommonPage {
    public ElementsCollection currencyDropdowns = $$("button.currency-dropdown__trigger");

    public ElementsCollection currenciesList = $$("li.currency-dropdown__item");

    public SelenideElement amountInput = $(byId("sell-amount"));

    public SelenideElement exchangeRateElement = $("p.exchange-rates-calculator__description");

    public SelenideElement sellAmount = $(byId("sell-amount"));

    public SelenideElement buyAmount = $(byId("buy-amount"));
}

