package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.BurgerMenuSteps;
import ge.tbc.testautomation.steps.LocationsSearchSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import ge.tbc.testautomation.util.Retry;
import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Description("SCRUM-T5")
public class SearchBranchWithValidDataTest extends BaseTest {
    NavigationSteps navigationSteps;
    BurgerMenuSteps burgerMenuSteps;
    LocationsSearchSteps locationsSearchSteps;

    @BeforeClass
    public void innerSetUp() {
        navigationSteps = new NavigationSteps();
        burgerMenuSteps = new BurgerMenuSteps();
        locationsSearchSteps = new LocationsSearchSteps();
    }

    @Test(
            priority = 1,
            description = "მისამართების გვერდზე გადასვლა"
    )
    public void navigateToAddressesPage() {
        if (isMobile) {
            burgerMenuSteps.openBurgerMenu()
                    .navigateToPage(LOCATIONS_TEXT)
                    .validateLocationsPage(LOCATIONS_PAGE_TEXT);
        } else {
            navigationSteps.navigateToForMe()
                    .navigateToLocations()
                    .verifyBreadcrumbContains(LOCATIONS_TEXT);
        }
    }

    @Test(
            priority = 2,
            description = "'ფილიალები' ტაბის არჩევა"
    )
    public void selectBranchesTab() {
        locationsSearchSteps.selectBranchesTab()
                .validateTabIsActive(BLUE, BRANCHES_INACTIVE_ERROR_MESSAGE);
    }

    @Test(
            priority = 3,
            description = "ქალაქის არჩევა"
    )
    public void selectCity() {
        locationsSearchSteps.selectCityFromDropdown(VALID_CITY)
                .validateCityFound(VALID_CITY);
    }

    @Retry(count = 3)
    @Test(
            priority = 4,
            description = "ლოკაციის არჩევა"
    )
    public void selectLocation() {
        locationsSearchSteps.setValueToLocationInput(VALID_LOCATION)
                .validateLocationFound(VALID_LOCATION);
    }
}