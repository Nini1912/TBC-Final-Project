package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.LOCATIONS_PAGE_TEXT;
import static ge.tbc.testautomation.data.Constants.LOCATIONS_TEXT;

public class BurgerMenuTest extends BaseTest{
    BurgerMenuSteps burgerMenuSteps;

    @BeforeClass
    public void innerSetUp() {
        burgerMenuSteps = new BurgerMenuSteps();
    }

    @BeforeMethod
    @Override
    public void methodSetUp() {
    }

    @Test(
            priority = 1,
            description = "ნავიგაციის ბარზე გადასვლა"
    )
    public void openNavigationBar() {
       burgerMenuSteps.openBurgerMenu();
    }

    @Test(
            priority = 2,
            description = "ძირითადი ლინკების შემოწმება"
    )
    public void verifyMainNavigationLinks() {
        burgerMenuSteps.verifyLinks();
    }

    @Test(
            priority = 3,
            description = "კონკრეტულ ლინკზე გადასვლა"
    )
    public void navigateToAddresses() {
        burgerMenuSteps.navigateToPage(LOCATIONS_TEXT).validateLocationsPage(LOCATIONS_PAGE_TEXT);
    }
}