package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.HeaderMenuPage;
import ge.tbc.testautomation.pages.LoansCalculatorPage;

import static com.codeborne.selenide.Condition.visible;

public class AcceptCookiesSteps {
    HeaderMenuPage headerMenuPage = new HeaderMenuPage();
    LoansCalculatorPage loansCalculatorPage = new LoansCalculatorPage();

    public AcceptCookiesSteps acceptCookies() {
        if (headerMenuPage.acceptCookiesButton.is(visible)) {
            headerMenuPage.acceptCookiesButton.click();
        }
        return this;
    }

    public AcceptCookiesSteps acceptCookiesOnNewTab(){
        if (loansCalculatorPage.acceptCookiesButton.is(visible)) {
            loansCalculatorPage.acceptCookiesButton.click();
        }
        return this;
    }
}
