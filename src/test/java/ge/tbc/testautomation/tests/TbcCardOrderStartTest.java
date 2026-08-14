package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.LinkDataProvider;
import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import ge.tbc.testautomation.steps.OrderCardSteps;
import ge.tbc.testautomation.util.Retry;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class TbcCardOrderStartTest extends BaseTest {
    NavigationSteps navigationSteps;
    BurgerMenuSteps burgerMenuSteps;
    OrderCardSteps orderCardSteps;

    @BeforeClass
    public void innerSetUp() {
        navigationSteps = new NavigationSteps();
        burgerMenuSteps = new BurgerMenuSteps();
        orderCardSteps = new OrderCardSteps();
    }

    @Test(
            priority = 1,
            description = "თიბისი ბარათის გვერდზე გადასვლა"
//            dataProvider = "LinkDataProvider",
//            dataProviderClass = LinkDataProvider.class
    )
    public void navigateToTbcCardPage() {
        if (isMobile) {
            burgerMenuSteps.openBurgerMenu()
                    .navigateToPage("ბარათები")
                    .navigateToSubPage("/ka/tbc-card")
                    .validateTbcCardPage();
        } else {
            navigationSteps.navigateToForMe()
                    .navigateToTbcCards()
                    .verifyBreadcrumbContains(TBC_CARD_TEXT);
        }
    }

    @Test(
            priority = 2,
            description = "ბარათის აღების დაწყება"
    )
    public void startGettingCard() {
        orderCardSteps.startOrder()
                .validateOrderStart();
    }
}