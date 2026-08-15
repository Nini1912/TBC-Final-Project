package ge.tbc.testautomation.data;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.regex.Pattern;

public class Constants {
    public static final String BASE_URL = "https://www.tbcbank.ge/";
    public static final String MOBILE_RESOLUTION = "390x844";

    public static final String CURRENCY_RATES_TEXT = "ვალუტის კურსები";
    public static final String CURRENCY_RATES_MOBILE_TEXT = "ვალუტის კურსი";
    public static final String EUR = "EUR" ;
    public static final String GEL = "GEL" ;
    public static final String INVALID_CURRENCY_INPUT = "-2e0" ;
    public static final String VALID_CURRENCY_INPUT = "200" ;
    public static final String CORRECTED_CURRENCY_INPUT = "20" ;
    public static final String CURRENCY_ERROR_MESSAGE = "კონვერტაციის შედეგად მიღებული თანხა არასწორია";

    public static final String LOANS_TEXT = "სესხები";
    public static final String LOANS_TAB_ERROR_MESSAGE = "სესხის თანხით ტაბი აქტიური არ არის. Background:";
    public static final String INVALID_LOAN_REQUESTED_AMOUNT = "150";
    public static final String VALID_LOAN_REQUESTED_AMOUNT = "10000";
    public static final String INVALID_LOAN_PERIOD = "2";
    public static final String VALID_LOAN_PERIOD = "24";
    public static final String LOANS_MONTHLY_PAYMENT_ERROR_MESSAGE = "ყოველთვიური შენატანი არასწორია";
    public static final String LOANS_CALCULATOR_ERROR_MESSAGE = "სესხის კალკულატორის ახალი tab არ გაიხსნა";
    public static final String WHITE = "255, 255, 255";

    public static final String LOCATIONS_TEXT = "მისამართები";
    public static final String FILTER_247 = "24/7";

    public static final String BLUE = "0, 173, 238";
    public static final String BRANCHES_INACTIVE_ERROR_MESSAGE = "ფილიალების ტაბი აქტიური არ არის. ";

    public static final String INVALID_CITY = "დუბაი";
    public static final String VALID_CITY = "თბილისი";
    public static final String INVALID_LOCATION = "ბეიკერსტრიტი";
    public static final String VALID_LOCATION = "პეკინი";
    public static final String NOT_FOUND_MESSAGE = "No items found";
    public static final String NOT_SEARCHING_MESSAGE = "არ იძებნება";

    public static final String TBC_CARD_TEXT = "თიბისი ბარათი";

    public static final String LOCATIONS_PAGE_TEXT = "ფილიალი, ბანკომატი და თანხის მიმღები";

    public static final String SUB_LINK_LOANS = "/ka/loans";
    public static final String SUB_LINK_TBC_CARD = "/ka/tbc-card";
    public static final String SUB_LINK_TBC_CARD_TEXT = "ბარათები";
}
