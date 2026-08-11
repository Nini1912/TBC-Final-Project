package ge.tbc.testautomation.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import jdk.jfr.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Description("SCRUM-T6")
public class LoanCalculatorWithNonvalidDataTest {
    private String originalWindow;
    private String calculatorWindow;

    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;

        open("https://www.tbcbank.ge/");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        originalWindow = WebDriverRunner.getWebDriver().getWindowHandle();
    }

    @Test(priority = 1, description = "ნავიგაციის ბარიდან სესხების გვერდზე გადასვლა")
    public void navigateToLoansPage() {
        SelenideElement acceptCookiesButton = $(byTagAndText("button", "თანხმობა"));
        acceptCookiesButton
                .shouldBe(visible)
                .click();

        SelenideElement  forMeMenu= $(byTagAndText("button", "ჩემთვის"));
        forMeMenu.shouldBe(visible).click();

        SelenideElement loansMenu = $(byTagAndText("span", "სესხები"));
        loansMenu.shouldBe(visible).click();

        $("body").shouldHave(text("სესხები"));
    }

    @Test(priority = 2, description = "სესხის კალკულატორის გვერდზე გადასვლა")
    public void openLoanCalculator() {
        String oldWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        Set<String> oldWindows = WebDriverRunner.getWebDriver().getWindowHandles();
        SelenideElement loanRequestBtn = $(byTagAndText("button", "სესხის მოთხოვნა"));
        loanRequestBtn.shouldBe(visible).click();

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

        $("body").shouldBe(visible);

        SelenideElement acceptCookiesButton =
                $$(byId("acceptAllCookies"))
                        .findBy(visible);

        acceptCookiesButton.click();
        acceptCookiesButton
                .shouldBe(visible)
                .click();
    }

    @Test(priority = 3, description = "დარწმუნება, რომ 'სესხის თანხით' ტაბი აქტიურია")
    public void verifyLoanAmountTabIsActive() {
        SelenideElement byLoanAmount = $(byTagAndText("li", "სესხის თანხით"));
        byLoanAmount.shouldBe(visible);

        String backgroundColor = byLoanAmount.getCssValue("background-color");

        Assert.assertTrue(
                backgroundColor.contains("255, 255, 255"),
                "სესხის თანხით ტაბი აქტიური არ არის. Background: "
                        + backgroundColor
        );
    }

    @Test(
            priority = 4,
            description = "სესხის თანხის შეყვანა"
    )
    public void enterLoanAmount() {

        SelenideElement loanAmountInput =
                $(byId("standard-calculator-amount"));

        loanAmountInput.shouldBe(visible);

        String requestedAmount = "150";

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
                loanAmountInput,
                requestedAmount
        );

        SelenideElement limits =
                $x("//p[contains(text(), 'მინ.')]");

        String limitsText =
                limits
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
            loanAmountInput.shouldHave(value(minAmount));

        } else if (requested > max) {
            loanAmountInput.shouldHave(value(maxAmount));

        } else {
            loanAmountInput.shouldHave(value(requestedAmount));
        }
    }

    @Test(
            priority = 5,
            description = "სესხის ვადის არჩევა"
    )
    public void selectLoanTerm() {
        SelenideElement loanPeriodInput =
                $(byId("standard-calculator-period"));

        String period = "2";

        String minPeriod = "3";
        String maxPeriod = "48";

        loanPeriodInput
                .shouldBe(visible)
                .setValue(period);

        if(Integer.parseInt(period) < Integer.parseInt(minPeriod)){
            loanPeriodInput
                    .shouldHave(value(minPeriod));
        }else if(Integer.parseInt(period) > Integer.parseInt(maxPeriod)){
            loanPeriodInput
                    .shouldHave(value(maxPeriod));
        }else {
            loanPeriodInput.shouldBe(value(period));
        }
    }

    @Test(priority = 6, description = "გამოთვლილი მონაცემების შემოწმება")
    public void verifyCalculatedData() {
        SelenideElement monthlyPayment =
                $(byId("standard-calculator-result-payment"));

        double loanAmount = 200;
        double annualInterestRate = 9.9;
        int months = 3;

        double monthlyCommission = 0;

        double monthlyRate =
                annualInterestRate / 12 / 100;

        double annuityPayment =
                loanAmount *
                        (monthlyRate * Math.pow(1 + monthlyRate, months))
                        /
                        (Math.pow(1 + monthlyRate, months) - 1);

        long expectedMonthlyPayment =
                Math.round(annuityPayment + monthlyCommission);

        monthlyPayment
                .shouldBe(visible);

        String paymentText =
                monthlyPayment.getText();

        System.out.println("Payment text: " + paymentText);

        double actualMonthlyPayment =
                Double.parseDouble(
                        paymentText
                                .replace("D", "")
                                .replace("₾", "")
                                .replace(",", "")
                                .trim()
                );

        long roundedActualMonthlyPayment =
                Math.round(actualMonthlyPayment);

        Assert.assertEquals(
                roundedActualMonthlyPayment,
                expectedMonthlyPayment,
                "ყოველთვიური შენატანი არასწორია"
        );
    }


    @AfterClass
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}