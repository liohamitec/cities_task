package datahandlers;

import exceptions.WrongInputException;
import org.apache.log4j.Logger;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Greeter {

    private final static String BUNDLE_NAME = "data";
    private final static ResourceBundle res = ResourceBundle.getBundle(BUNDLE_NAME);

    final static Logger logger = Logger.getLogger(DataHandler.class);

    public String createGreet(String city, ZoneId id) {
        String message;
        if (city == null || id == null) {
            message = res.getString("msgError.nullParameter");
            logger.error(message);
            throw new WrongInputException(message);
        } else if (city.isEmpty()) {
            message = res.getString("msgError.emptyCityParam");
            logger.error(message);
            throw new WrongInputException(message);
        }

        int curHour = ZonedDateTime.now(id).getHour();
        logger.info("Hour in specific timezone: " + curHour);

        String locale = Locale.getDefault().getCountry();
        logger.info("Locale: " + locale);

        String greetingStr = getGreetingByTimeCode(curHour);
        greetingStr = greetingStr + " " + city;
        logger.info("Greeter class returned string: " + greetingStr);

        return greetingStr;
    }

    private String getGreetingByTimeCode(int hour) {  //получаем приветствие по текущему времени
        if (hour >= 6 && hour < 9)
            return res.getString("msgGreeting.morning");
        if (hour >= 9 && hour < 19)
            return res.getString("msgGreeting.day");
        if (hour >= 19 && hour < 23)
            return res.getString("msgGreeting.evening");

        return res.getString("msgGreeting.night");
    }

}
