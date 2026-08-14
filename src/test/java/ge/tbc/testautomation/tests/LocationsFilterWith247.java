package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.LocationsFilterSteps;
import ge.tbc.testautomation.steps.NavigationSteps;
import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Description("SCRUM_T9")
public class LocationsFilterWith247 extends BaseTest {
    AcceptCookiesSteps acceptCookiesSteps;
    NavigationSteps navigationSteps;
    LocationsFilterSteps locationsFilterSteps;

    @BeforeClass
    public void innerSetUp() {
        acceptCookiesSteps = new AcceptCookiesSteps();
        navigationSteps = new NavigationSteps();
        locationsFilterSteps = new LocationsFilterSteps();
    }

    @Test(
            priority = 1,
            description = "მისამართების გვერდზე გადასვლა"
    )
    public void navigateToAddressesPage() {
        acceptCookiesSteps.acceptCookies();
        navigationSteps.navigateToForMe()
                .navigateToLocations().verifyBreadcrumbContains(LOCATIONS_TEXT);
    }

    @Test(
            priority = 2,
            description = "'24/7' ფილტრის არჩევა"
    )
    public void select247Filter() {
        locationsFilterSteps.append247Filter()
                .validateFilterIsAppended()
                .validate247FilterWorks(FILTER_247);
    }
}
