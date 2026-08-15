package ge.tbc.testautomation.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.steps.AcceptCookiesSteps;
import ge.tbc.testautomation.steps.SurveyPopupSteps;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static ge.tbc.testautomation.data.Constants.BASE_URL;
import static ge.tbc.testautomation.data.Constants.MOBILE_RESOLUTION;

public class BaseTest {
    protected boolean isMobile;
    AcceptCookiesSteps acceptCookiesSteps;
    SurveyPopupSteps surveyPopupSteps;

    @BeforeClass
    @Parameters("resolution")
    public void setUp(String resolution) {

        isMobile = resolution.equals(MOBILE_RESOLUTION);

        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;

        open(BASE_URL);

        String[] dimensions = resolution.split("x");

        WebDriverRunner.getWebDriver()
                .manage()
                .window()
                .setSize(new Dimension(
                        Integer.parseInt(dimensions[0]),
                        Integer.parseInt(dimensions[1])
                ));
        acceptCookiesSteps = new AcceptCookiesSteps();
        surveyPopupSteps = new SurveyPopupSteps();
    }

    @BeforeMethod
    public void methodSetUp(){
        acceptCookiesSteps.acceptCookies();
        surveyPopupSteps.dismissIfPresent();
    }
    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}