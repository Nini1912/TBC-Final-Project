package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;

public class LoansCalculatorPage {
    public SelenideElement loanRequestBtn = $(byTagAndText("button", "სესხის მოთხოვნა"));
    public SelenideElement acceptCookiesButton = $$(byId("acceptAllCookies")).findBy(visible);
    public SelenideElement byLoanAmount = $(byTagAndText("li", "სესხის თანხით"));
    public SelenideElement loanAmountInput = $(byId("standard-calculator-amount"));
    public SelenideElement limits = $x("//p[contains(text(), 'მინ.')]");
    public SelenideElement loanPeriodInput = $(byId("standard-calculator-period"));
    public SelenideElement rangeElement = $(byId("standard-calc-period-slider"));
    public SelenideElement monthlyPayment = $(byId("standard-calculator-result-payment"));
    public SelenideElement loanResultAmount = $(byId("standard-calculator-result-amount"));
    public SelenideElement loanResultRate = $(byId("standard-calculator-result-rate"));
    public SelenideElement loanResultPeriod = $(byId("standard-calculator-result-period"));
    public SelenideElement sliderOrigin = $(".noUi-origin");

}
