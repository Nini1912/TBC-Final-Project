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

@Description("SCRUM-T1")
public class LoanCalculatorWithValidDataTest {
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

    @Test(priority = 4, description = "სესხის თანხის შეყვანა")
    public void enterLoanAmount() {
        SelenideElement loanAmountInput = $(byId("standard-calculator-amount"));
        loanAmountInput.shouldBe(visible);

        JavascriptExecutor js =
                (JavascriptExecutor) WebDriverRunner.getWebDriver();

        js.executeScript(
                "const input = arguments[0];" +
                        "const setter = Object.getOwnPropertyDescriptor(" +
                        "HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(input, '10000');" +
                        "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "input.dispatchEvent(new Event('change', { bubbles: true }));",
                loanAmountInput
        );

        loanAmountInput.shouldHave(value("10000"));
    }

    @Test(priority = 5, description = "სესხის ვადის არჩევა")
    public void selectLoanTerm() {
        SelenideElement loanPeriodInput =
                $(byId("standard-calculator-period"));

        SelenideElement sliderOrigin =
                $(".noUi-origin");

        String period = "24";

        double minPeriod = 3;
        double maxPeriod = 48;

        double expectedPercentage =
                Math.round(
                        ((Double.parseDouble(period) - minPeriod)
                                / (maxPeriod - minPeriod) * 100) * 10000
                ) / 10000.0;

        loanPeriodInput
                .shouldBe(visible)
                .setValue(period);

        loanPeriodInput
                .shouldHave(value(period));

        sliderOrigin
                .shouldBe(visible)
                .shouldHave(attributeMatching(
                        "style",
                        ".*" + expectedPercentage + ".*"
                ));

        loanPeriodInput
                .shouldHave(value(period));
    }

    @Test(priority = 6, description = "გამოთვლილი მონაცემების შემოწმება")
    public void verifyCalculatedData() {
        SelenideElement monthlyPayment =
                $(byId("standard-calculator-result-payment"));

        double loanAmount = 10_000;
        double annualInterestRate = 9.9;
        int months = 24;

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