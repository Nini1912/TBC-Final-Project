package ge.tbc.testautomation.steps;

import com.codeborne.selenide.conditions.Or;
import ge.tbc.testautomation.pages.TbcCardPage;

import static com.codeborne.selenide.Condition.visible;

public class OrderCardSteps {
    TbcCardPage tbcCardPage = new TbcCardPage();
    public OrderCardSteps startOrder(){
        tbcCardPage.getCardButton
                .shouldBe(visible)
                .click();

        return this;
    }

    public OrderCardSteps validateOrderStart(){
        tbcCardPage.popupBanner
                .shouldBe(visible);
        return this;
    }
}
