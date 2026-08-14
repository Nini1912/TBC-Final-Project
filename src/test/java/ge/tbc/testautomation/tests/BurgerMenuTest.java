package ge.tbc.testautomation.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.data.LinkDataProvider;
import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ge.tbc.testautomation.data.Constants.LOCATIONS_PAGE_TEXT;
import static ge.tbc.testautomation.data.Constants.LOCATIONS_TEXT;

public class BurgerMenuTest {
    AcceptCookiesSteps acceptCookiesSteps;
    BurgerMenuSteps burgerMenuSteps;

    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;

        open("https://www.tbcbank.ge/");

        WebDriverRunner.getWebDriver()
                .manage()
                .window()
                .setSize(new org.openqa.selenium.Dimension(390, 844));

        acceptCookiesSteps = new AcceptCookiesSteps();
        burgerMenuSteps = new BurgerMenuSteps();
    }

    @Test(
            priority = 1,
            description = "ნავიგაციის ბარზე გადასვლა"
    )
    public void openNavigationBar() {
       acceptCookiesSteps.acceptCookies();
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