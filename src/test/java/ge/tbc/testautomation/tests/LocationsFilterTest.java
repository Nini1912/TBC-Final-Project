package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.LocationsFilterSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class LocationsFilterTest extends BaseTest {
    AcceptCookiesSteps acceptCookiesSteps;
    NavigationSteps navigationSteps;
    LocationsFilterSteps locationsFilterSteps;
    BurgerMenuSteps burgerMenuSteps;

    @BeforeClass
    public void innerSetUp() {
        acceptCookiesSteps = new AcceptCookiesSteps();
        navigationSteps = new NavigationSteps();
        locationsFilterSteps = new LocationsFilterSteps();
        burgerMenuSteps = new BurgerMenuSteps();
    }

    @Test(priority = 1, description = "მისამართების გვერდზე გადასვლა")
    public void navigateToAddressesPage() {
        acceptCookiesSteps.acceptCookies();

//        if (isMobile) {
//            burgerMenuSteps.openBurgerMenu()
//                    .navigateToPage(LOCATIONS_TEXT)
//                    .validateLocationsPage(LOCATIONS_PAGE_TEXT);
//        } else {
//            navigationSteps.navigateToForMe()
//                    .navigateToLocations()
//                    .verifyBreadcrumbContains(LOCATIONS_TEXT);
//        }
    }
//
//    @DataProvider(name = "filterCombinations")
//    public Object[][] filterCombinations() {
//        return new Object[][]{
//                {true, false},
//                {false, true},
//                {true, true}
//        };
//    }
//
//    @Test(priority = 2, dependsOnMethods = "navigateToAddressesPage", dataProvider = "filterCombinations")
//    public void applyAndValidateFilters(boolean apply247, boolean applyOpen) {
//        locationsFilterSteps.resetFilters();
//        if (apply247) {
//            locationsFilterSteps.append247Filter()
//                    .validateFilterIsAppended()
//                    .validate247FilterWorks(FILTER_247);
//        }
//        if (applyOpen) {
//            locationsFilterSteps.appendOpenFilter()
//                    .validateFilterIsAppended()
//                    .validateOpenFilterWorks();
//        }
//    }
}