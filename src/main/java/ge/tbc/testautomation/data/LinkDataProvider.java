package ge.tbc.testautomation.data;

import org.testng.annotations.DataProvider;

public class LinkDataProvider {
    @DataProvider(name = "LinkDataProvider")
    public static Object[][] linkDataProvider(){

        return new Object[][]{
                {"ჩემთვის"},
                {"სესხები"},
                {"ბარათები"},
                {"ანაბრები"},
                {"ციფრული სერვისები"},
                {"სხვა პროდუქტები"},
                {"კონცეპტი"},
                {"ახალი თაობისთვის"},
                {"ემიგრანტებისთვის"},
                {"ექსპატებისთვის"},
                {"ვალუტის კურსები"},
                {"შეთავაზებები"},
                {"ერთგულება"},
                {"მისამართები"}
        };
    }
}