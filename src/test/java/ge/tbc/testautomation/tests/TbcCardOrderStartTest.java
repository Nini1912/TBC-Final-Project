package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import ge.tbc.testautomation.steps.OrderCardSteps;
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
    )
    public void navigateToTbcCardPage() {
        if (isMobile) {
            burgerMenuSteps.openBurgerMenu()
                    .navigateToPage(SUB_LINK_TBC_CARD_TEXT)
                    .navigateToSubPage(SUB_LINK_TBC_CARD)
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