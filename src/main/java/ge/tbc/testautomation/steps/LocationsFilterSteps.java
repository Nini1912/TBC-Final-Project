package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.LocationsPage;
import org.testng.Assert;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;

public class LocationsFilterSteps {
    LocationsPage locationsPage = new LocationsPage();

    public LocationsFilterSteps append247Filter(){
        locationsPage.filter247
                .shouldBe(visible)
                .click();

        locationsPage.filter247.$("input.tbcx-pw-chip__input")
                .shouldBe(selected);
        return this;
    }

    public LocationsFilterSteps appendOpenFilter(){
        locationsPage.filterOpen
                .shouldBe(visible)
                .click();

        locationsPage.filterOpen.$("input.tbcx-pw-chip__input")
                .shouldBe(selected);

        return this;
    }

    public LocationsFilterSteps validateFilterIsAppended(){
        locationsPage.items.first().shouldBe(visible);
        return this;
    }

    public LocationsFilterSteps validate247FilterWorks(String filter){
        locationsPage.descriptions.shouldHave(sizeGreaterThan(0));
        locationsPage.descriptions.forEach(el -> el.shouldHave(text(filter)));
        return this;
    }

    public LocationsFilterSteps validateOpenFilterWorks(){
        locationsPage.items.shouldHave(sizeGreaterThan(0));

        locationsPage.descriptions.shouldBe(CollectionCondition.empty);

        locationsPage.descriptions.shouldHave(sizeGreaterThan(0));

        for (SelenideElement description : locationsPage.descriptions) {
            String scheduleText = description.getText();
            boolean isValid = isCurrentlyOpen(scheduleText);
            Assert.assertTrue(isValid);
        }
        return this;
    }

    private static final Map<String, DayOfWeek> DAY_MAP = Map.of(
            "ორშაბათი", DayOfWeek.MONDAY,
            "სამშაბათი", DayOfWeek.TUESDAY,
            "ოთხშაბათი", DayOfWeek.WEDNESDAY,
            "ხუთშაბათი", DayOfWeek.THURSDAY,
            "პარასკევი", DayOfWeek.FRIDAY,
            "შაბათი", DayOfWeek.SATURDAY,
            "კვირა", DayOfWeek.SUNDAY
    );

    private static final Pattern SCHEDULE_ENTRY = Pattern.compile(
            "([ა-ჰ]+)(?:-([ა-ჰ]+))?:\\s*(\\d{2}:\\d{2})-(\\d{2}:\\d{2})"
    );

    public boolean isCurrentlyOpen(String scheduleText) {
        if (scheduleText.contains("24/7")) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        Matcher matcher = SCHEDULE_ENTRY.matcher(scheduleText);

        while (matcher.find()) {
            DayOfWeek startDay = DAY_MAP.get(matcher.group(1));
            DayOfWeek endDay = matcher.group(2) != null ? DAY_MAP.get(matcher.group(2)) : startDay;

            if (startDay == null || endDay == null || !isDayInRange(currentDay, startDay, endDay)) {
                continue;
            }

            LocalTime open = LocalTime.parse(matcher.group(3));
            LocalTime close = LocalTime.parse(matcher.group(4));

            if (!currentTime.isBefore(open) && !currentTime.isAfter(close)) {
                return true;
            }
        }

        return false;
    }

    private boolean isDayInRange(DayOfWeek current, DayOfWeek start, DayOfWeek end) {
        int cur = current.getValue();
        int s = start.getValue();
        int e = end.getValue();
        return s <= e ? (cur >= s && cur <= e) : (cur >= s || cur <= e);
    }

    public LocationsFilterSteps resetFilters(){
        if (locationsPage.filter247.$("input.tbcx-pw-chip__input").is(selected)) {
            locationsPage.filter247.click();
        }
        if (locationsPage.filterOpen.$("input.tbcx-pw-chip__input").is(selected)) {
            locationsPage.filterOpen.click();
        }
        return this;
    }
}
