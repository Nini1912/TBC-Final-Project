package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.CurrencyCalculatorPage;
import org.testng.Assert;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;

public class CurrencyCalculateSteps {
    CurrencyCalculatorPage currencyCalculatorPage = new CurrencyCalculatorPage();

    public CurrencyCalculateSteps validateDropDownsNumber (){
        currencyCalculatorPage.currencyDropdowns.shouldHave(size(2));
        return this;
    }

    public CurrencyCalculateSteps selectSellCurrency (String currencyCode){
        currencyCalculatorPage.currencyDropdowns.get(0)
                .shouldBe(visible)
                .click();

        currencyCalculatorPage.currenciesList
                .filterBy(text(currencyCode))
                .first()
                .shouldBe(visible)
                .click();

        return this;
    }

    public CurrencyCalculateSteps selectBuyCurrency (String currencyCode){
        currencyCalculatorPage.currencyDropdowns.get(1)
                .shouldBe(visible)
                .click();

        currencyCalculatorPage.currenciesList
                .filterBy(text(currencyCode))
                .first()
                .shouldBe(visible)
                .click();

        return this;
    }

    public CurrencyCalculateSteps verifySellCurrencySelected(String currencyCode) {
        currencyCalculatorPage.currencyDropdowns.get(0)
                .shouldHave(text(currencyCode));

        return this;
    }

    public CurrencyCalculateSteps verifyBuyCurrencySelected(String currencyCode) {
        currencyCalculatorPage.currencyDropdowns.get(1)
                .shouldHave(text(currencyCode));

        return this;
    }

    public CurrencyCalculateSteps enterInvalidSellAmount(String invalidSellAmount, String correctedSellAmount){
        currencyCalculatorPage.amountInput
                .shouldBe(visible)
                .setValue(invalidSellAmount);

        currencyCalculatorPage.amountInput.shouldHave(value(correctedSellAmount));

        return this;
    }

    public CurrencyCalculateSteps enterValidSellAmount(String validSellAmount){
        currencyCalculatorPage.amountInput
                .shouldBe(visible)
                .setValue(validSellAmount);

        currencyCalculatorPage.amountInput
                .shouldHave(value(validSellAmount));

        return this;
    }

    public double getExchangeRate(){
        currencyCalculatorPage.exchangeRateElement.shouldBe(visible);

        String exchangeRateText =
                currencyCalculatorPage.exchangeRateElement.getText();

        String rateString = exchangeRateText
                .replaceAll(".*=\\s*", "")
                .replaceAll("\\s*GEL.*", "")
                .trim();

        double exchangeRate =
                Double.parseDouble(rateString);

        return exchangeRate;
    }

    public double CalculateExpectedBuyAmount(double exchangeRate){
        currencyCalculatorPage.sellAmount.shouldBe(visible);

        double enteredAmount =
                Double.parseDouble(currencyCalculatorPage.sellAmount.getValue());

        double expectedAmount =
                enteredAmount * exchangeRate;

        return expectedAmount;
    }

    public CurrencyCalculateSteps verifyConvertedAmount(double expectedAmount, String errorMessage){
        currencyCalculatorPage.buyAmount.shouldBe(visible);

        currencyCalculatorPage.buyAmount.shouldHave(
                match(
                        "converted amount",
                        element -> {
                            String value =
                                    element.getAttribute("value");

                            if (value == null || value.isEmpty()) {
                                return false;
                            }

                            try {
                                double actualAmount =
                                        Double.parseDouble(value);

                                return Math.abs(
                                        actualAmount - expectedAmount
                                ) <= 0.01;

                            } catch (NumberFormatException e) {
                                return false;
                            }
                        }
                )
        );

        double actualAmount =
                Double.parseDouble(currencyCalculatorPage.buyAmount.getValue());

        Assert.assertEquals(
                actualAmount,
                expectedAmount,
                0.01,
                errorMessage
        );

        return this;
    }

    public CurrencyCalculateSteps selectSellCurrencyMobile (String currencyCode){
        currencyCalculatorPage.currencyDropdowns.get(0)
                .shouldBe(visible)
                .click(usingJavaScript());

        currencyCalculatorPage.currenciesList
                .filterBy(text(currencyCode))
                .first()
                .shouldBe(visible)
                .click();

        return this;
    }

    public CurrencyCalculateSteps selectBuyCurrencyMobile (String currencyCode){
        currencyCalculatorPage.currencyDropdowns.get(1)
                .shouldBe(visible)
                .click(usingJavaScript());

        currencyCalculatorPage.currenciesList
                .filterBy(text(currencyCode))
                .first()
                .shouldBe(visible)
                .click();

        return this;
    }
}
