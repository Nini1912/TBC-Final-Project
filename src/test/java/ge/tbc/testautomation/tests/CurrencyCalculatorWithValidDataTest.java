package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.CurrencyCalculateSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import ge.tbc.testautomation.util.Retry;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class CurrencyCalculatorWithValidDataTest extends BaseTest {
    AcceptCookiesSteps acceptCookiesSteps;
    NavigationSteps navigationSeps;
    BurgerMenuSteps burgerMenuSteps;
    CurrencyCalculateSteps currencyCalculateSteps;

    @BeforeClass
    public void innerSetUp(){
        acceptCookiesSteps = new AcceptCookiesSteps();
        navigationSeps = new NavigationSteps();
        burgerMenuSteps = new BurgerMenuSteps();
        currencyCalculateSteps = new CurrencyCalculateSteps();
    }

    @Test(
            priority = 1,
            description = "ვალუტის კურსის გვერდზე გადასვლა"
    )
    public void navigateToCurrencyRatesPage() {
        acceptCookiesSteps.acceptCookies();

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
            description = "თანხის შეყვანა"
    )
    public void enterAmount() {
        currencyCalculateSteps.enterValidSellAmount(VALID_CURRENCY_INPUT);
    }

    @Retry(count = 2)
    @Test(
            priority = 4,
            description = "კონვერტაციის შედეგად მიღებული თანხის გადამოწმება"
    )
    public void verifyConvertedAmount() {
        double exchangeRate = currencyCalculateSteps.getExchangeRate();
        double expectedAmount = currencyCalculateSteps.CalculateExpectedBuyAmount(exchangeRate);
        currencyCalculateSteps.verifyConvertedAmount(expectedAmount, CURRENCY_ERROR_MESSAGE);
    }
}