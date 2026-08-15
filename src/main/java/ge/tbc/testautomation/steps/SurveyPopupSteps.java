package ge.tbc.testautomation.steps;

import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.CommonPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class SurveyPopupSteps {
    CommonPage commonPage = new CommonPage();

    public SurveyPopupSteps dismissIfPresent() {
        if (commonPage.surveyFrame.is(visible, Duration.ofSeconds(2))) {
            switchTo().frame(commonPage.surveyFrame);
            SelenideElement closeBtn = $("[data-aut='button-x-close']");
            if (closeBtn.is(visible, Duration.ofSeconds(2))) {
                closeBtn.click();
            }
            switchTo().defaultContent();
        }
        return this;
    }
}