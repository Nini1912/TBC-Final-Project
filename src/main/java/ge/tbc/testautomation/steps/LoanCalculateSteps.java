package ge.tbc.testautomation.steps;

import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.pages.LoansCalculatorPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Set;

import static com.codeborne.selenide.Condition.*;

public class LoanCalculateSteps {
    LoansCalculatorPage loansCalculatorPage = new LoansCalculatorPage();

    public LoanCalculateSteps openNewTab(String calculatorWindow){
        String oldWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        Set<String> oldWindows = WebDriverRunner.getWebDriver().getWindowHandles();

        loansCalculatorPage.loanRequestBtn.shouldBe(visible).click();

        new WebDriverWait(
                WebDriverRunner.getWebDriver(),
                Duration.ofSeconds(10)
        ).until(driver ->
                driver.getWindowHandles().size() > oldWindows.size()
        );

        Set<String> newWindows =
                WebDriverRunner.getWebDriver().getWindowHandles();

        for (String window : newWindows) {
            if (!window.equals(oldWindow)) {
                calculatorWindow = window;
                WebDriverRunner.getWebDriver().switchTo().window(window);
                break;
            }
        }

        Assert.assertNotNull(
                calculatorWindow,
                "სესხის კალკულატორის ახალი tab არ გაიხსნა"
        );

//        $("body").shouldBe(visible);

        return this;
    }

    public LoanCalculateSteps btnIsActive(String errorMessage){
        loansCalculatorPage.byLoanAmount.shouldBe(visible);

        String backgroundColor = loansCalculatorPage.byLoanAmount.getCssValue("background-color");

        Assert.assertTrue(
                backgroundColor.contains("255, 255, 255"),
                errorMessage
                        + backgroundColor
        );

        return this;
    }

    public LoanCalculateSteps setLoanAmount(String requestedAmount){
        loansCalculatorPage.loanAmountInput.shouldBe(visible);

        JavascriptExecutor js =
                (JavascriptExecutor) WebDriverRunner.getWebDriver();

        js.executeScript(
                "const input = arguments[0];" +
                        "const value = arguments[1];" +
                        "const setter = Object.getOwnPropertyDescriptor(" +
                        "HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(input, value);" +
                        "input.dispatchEvent(" +
                        "new Event('input', { bubbles: true })" +
                        ");" +
                        "input.dispatchEvent(" +
                        "new Event('change', { bubbles: true })" +
                        ");",
                loansCalculatorPage.loanAmountInput,
                requestedAmount
        );

        return this;
    }

    public LoanCalculateSteps verifyLoanAmountIsSet(String requestedAmount){
        loansCalculatorPage.loanAmountInput.shouldHave(value(requestedAmount));

        return this;
    }

    public LoanCalculateSteps verifyLoanLimits(String requestedAmount){
        String limitsText =
                loansCalculatorPage.limits
                        .shouldBe(visible)
                        .getText();

        String minAmount =
                limitsText
                        .replaceAll(
                                ".*მინ\\.\\s*(\\d+)₾.*",
                                "$1"
                        );

        String maxAmount =
                limitsText
                        .replaceAll(
                                ".*მაქს\\.\\s*([\\d\\s]+)₾.*",
                                "$1"
                        )
                        .replace(" ", "");

        int requested =
                Integer.parseInt(requestedAmount);

        int min =
                Integer.parseInt(minAmount);

        int max =
                Integer.parseInt(maxAmount);

        if (requested < min) {
            loansCalculatorPage.loanAmountInput.shouldHave(value(minAmount));

        } else if (requested > max) {
            loansCalculatorPage.loanAmountInput.shouldHave(value(maxAmount));

        }

        return this;
    }

    public LoanCalculateSteps setInvalidLoanTerm(String invalidLoanTerm){
        loansCalculatorPage.loanPeriodInput
                .shouldBe(visible)
                .setValue(invalidLoanTerm);
        return this;
    }

    public LoanCalculateSteps setValidLoanTerm(String validLoanTerm){
        int maxPeriod = Integer.parseInt(loansCalculatorPage.rangeElement.getAttribute("max"));
        int minPeriod = Integer.parseInt(loansCalculatorPage.rangeElement.getAttribute("min"));

        double expectedPercentage =
                Math.round(
                        ((float) (Integer.parseInt(validLoanTerm) - minPeriod)
                                / (maxPeriod - minPeriod) * 100) * 10000
                ) / 10000.0;

        loansCalculatorPage.loanPeriodInput
                .shouldBe(visible)
                .setValue(validLoanTerm);

        loansCalculatorPage.loanPeriodInput
                .shouldHave(value(validLoanTerm));

        loansCalculatorPage.sliderOrigin
                .shouldBe(visible)
                .shouldHave(attributeMatching(
                        "style",
                        ".*" + expectedPercentage + ".*"
                ));

        loansCalculatorPage.loanPeriodInput
                .shouldHave(value(validLoanTerm));

        return this;
    }

    public LoanCalculateSteps validateLoanTermLimits(String InvalidLoanTerm){
        String maxPeriod = loansCalculatorPage.rangeElement.getAttribute("max");
        String minPeriod = loansCalculatorPage.rangeElement.getAttribute("min");

        if(Integer.parseInt(InvalidLoanTerm) < Integer.parseInt(minPeriod)){
            loansCalculatorPage.loanPeriodInput
                    .shouldHave(value(minPeriod));
        }else if(Integer.parseInt(InvalidLoanTerm) > Integer.parseInt(maxPeriod)){
            loansCalculatorPage.loanPeriodInput
                    .shouldHave(value(maxPeriod));
        }else {
            loansCalculatorPage.loanPeriodInput.shouldBe(value(InvalidLoanTerm));
        }

        return this;
    }

    public double calculateExpectedMonthlyPayment() {
        int loanAmount = Integer.parseInt(loansCalculatorPage.loanResultAmount.getText());

        double annualInterestRate = Double.parseDouble(
                loansCalculatorPage.loanResultRate.getText()
                        .replace("%-დან", "")
                        .trim()
        );

        int months = Integer.parseInt(loansCalculatorPage.loanResultPeriod.getText());
        double monthlyCommission = 0;
        double monthlyRate = annualInterestRate / 12 / 100;

        double annuityPayment =
                loanAmount *
                        (monthlyRate * Math.pow(1 + monthlyRate, months))
                        /
                        (Math.pow(1 + monthlyRate, months) - 1);

        return Math.round(annuityPayment + monthlyCommission);
    }

    public double getActualMonthlyPayment() {
        loansCalculatorPage.monthlyPayment.shouldBe(visible);

        String paymentText = loansCalculatorPage.monthlyPayment.getText();

        return Double.parseDouble(
                paymentText
                        .replace("D", "")
                        .replace("₾", "")
                        .replace(",", "")
                        .trim()
        );
    }

    public LoanCalculateSteps verifyCalculatedMonthlyPayment(String errorMessage) {
        long expectedMonthlyPayment = (long) calculateExpectedMonthlyPayment();
        long roundedActualMonthlyPayment = Math.round(getActualMonthlyPayment());

        Assert.assertEquals(
                roundedActualMonthlyPayment,
                expectedMonthlyPayment,
                errorMessage
        );

        return this;
    }
}
