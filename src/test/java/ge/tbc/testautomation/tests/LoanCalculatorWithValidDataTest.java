package ge.tbc.testautomation.tests;

import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.LoanCalculateSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Description("SCRUM-T1")
public class LoanCalculatorWithValidDataTest extends BaseTest {
    private String originalWindow;
    private String calculatorWindow;

    NavigationSteps navigationSeps;
    BurgerMenuSteps burgerMenuSteps;
    LoanCalculateSteps loanCalculateSteps;

    @BeforeClass
    public void innerSetUp() {
        originalWindow = WebDriverRunner.getWebDriver().getWindowHandle();

        navigationSeps = new NavigationSteps();
        burgerMenuSteps = new BurgerMenuSteps();
        loanCalculateSteps = new LoanCalculateSteps();
    }

    @Test(priority = 1, description = "ნავიგაციის ბარიდან სესხების გვერდზე გადასვლა")
    public void navigateToLoansPage() {
        if (isMobile) {
            burgerMenuSteps.openBurgerMenu()
                    .navigateToPage(LOANS_TEXT)
                    .navigateToSubPage(SUB_LINK_LOANS);
        } else {
            navigationSeps.navigateToForMe()
                    .navigateToLoans()
                    .verifyBreadcrumbContains(LOANS_TEXT);
        }
    }

    @Test(priority = 2, description = "სესხის კალკულატორის გვერდზე გადასვლა")
    public void openLoanCalculator() {
        loanCalculateSteps.openNewTab(calculatorWindow, LOANS_CALCULATOR_ERROR_MESSAGE);
        acceptCookiesSteps.acceptCookiesOnNewTab();
    }

    @Test(priority = 3, description = "დარწმუნება, რომ 'სესხის თანხით' ტაბი აქტიურია")
    public void verifyLoanAmountTabIsActive() {
        loanCalculateSteps.btnIsActive(WHITE, LOANS_TAB_ERROR_MESSAGE);
    }

    @Test(priority = 4, description = "სესხის თანხის შეყვანა")
    public void enterLoanAmount() {
        loanCalculateSteps.setLoanAmount(VALID_LOAN_REQUESTED_AMOUNT)
                .verifyLoanAmountIsSet(VALID_LOAN_REQUESTED_AMOUNT);
    }

    @Test(priority = 5, description = "სესხის ვადის არჩევა")
    public void selectLoanTerm() {
        loanCalculateSteps.setValidLoanTerm(VALID_LOAN_PERIOD);
    }

    @Test(priority = 6, description = "გამოთვლილი მონაცემების შემოწმება")
    public void verifyCalculatedData() {
        loanCalculateSteps.verifyCalculatedMonthlyPayment(LOANS_MONTHLY_PAYMENT_ERROR_MESSAGE);
    }
}