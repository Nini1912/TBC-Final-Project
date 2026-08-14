package ge.tbc.testautomation.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static ge.tbc.testautomation.data.Constants.BASE_URL;
import static ge.tbc.testautomation.data.Constants.MOBILE_RESOLUTION;

public class BaseTest {
    protected boolean isMobile;
    
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
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}