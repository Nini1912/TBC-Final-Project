package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.CurrencyCalculateSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class CurrencyCalculatorWithInvalidDataTest extends BaseTest {
    NavigationSteps navigationSeps;
    BurgerMenuSteps burgerMenuSteps;
    CurrencyCalculateSteps currencyCalculateSteps;

    @BeforeClass
    public void innerSetUp(){
        navigationSeps = new NavigationSteps();
        burgerMenuSteps = new BurgerMenuSteps();
        currencyCalculateSteps = new CurrencyCalculateSteps();
    }

    @Test(
            priority = 1,
            description = "ვალუტის კურსის გვერდზე გადასვლა"
    )
    public void navigateToCurrencyRatesPage() {
        String currencyText = isMobile
                ? CURRENCY_RATES_MOBILE_TEXT
                : CURRENCY_RATES_TEXT;

        if (isMobile) {
            burgerMenuSteps.openBurgerMenu()
                    .navigateToPage(CURRENCY_RATES_TEXT)
                    .validatePage(currencyText);
        } else {
            navigationSeps.navigateToForMe()
                    .navigateToCurrencyRate()
                    .verifyBreadcrumbContains(currencyText);
        }
    }

    @Test(
            priority = 2,
            description = "საწყისი და სამიზნე ვალუტის არჩევა"
    )
    public void selectCurrencies() {
        if (isMobile) {
            currencyCalculateSteps.validateDropDownsNumber()
                    .selectSellCurrencyMobile(EUR)
                    .selectBuyCurrencyMobile(GEL)
                    .verifySellCurrencySelected(EUR)
                    .verifyBuyCurrencySelected(GEL);
        } else {
            currencyCalculateSteps.validateDropDownsNumber()
                    .selectSellCurrency(EUR)
                    .selectBuyCurrency(GEL)
                    .verifySellCurrencySelected(EUR)
                    .verifyBuyCurrencySelected(GEL);
        }
    }

    @Test(
            priority = 3,
            description = "არასწორი სიმბოლოების შეყვანა"
    )
    public void enterInvalidAmount() {
        currencyCalculateSteps.enterInvalidSellAmount(INVALID_CURRENCY_INPUT, CORRECTED_CURRENCY_INPUT);
    }

    @Test(
            priority = 4,
            description = "კონვერტაციის შედეგად მიღებული თანხის გადამოწმება"
    )
    public void verifyConvertedAmount() {
        double exchangeRate = currencyCalculateSteps.getExchangeRate();
        double expectedBuyAmount = currencyCalculateSteps.CalculateExpectedBuyAmount(exchangeRate);
        currencyCalculateSteps.verifyConvertedAmount(expectedBuyAmount, CURRENCY_ERROR_MESSAGE);
    }
}