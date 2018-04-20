import datahandlers.Greeter;
import exceptions.WrongInputException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class GreeterTest {
    Greeter greeter = new Greeter();

    private final static String BUNDLE_NAME_EN = "data_en_US";
    private final static String BUNDLE_NAME_RU = "data_ru_RU";
    private final static ResourceBundle res = ResourceBundle.getBundle(BUNDLE_NAME_EN);

    final String city = "CITYNAME";
    final ZoneId GMT  = ZoneId.of("GMT");
    final ZoneId AU   = ZoneId.of("Australia/Sydney");
    final ZoneId CUBA = ZoneId.of("Cuba");

    @Test
    public void greetGMT() {
        String message = greeter.createGreet(city,GMT);
        String expectedMessage = getExpectedMessage(city,GMT);
        assertEquals(expectedMessage, message);
    }

    @Test
    public void greetCUBA() {
        String message = greeter.createGreet(city,CUBA);
        String expectedMessage = getExpectedMessage(city,CUBA);
        assertEquals(expectedMessage, message);
    }

    @Test
    public void greetAU() {
        String message = greeter.createGreet(city,AU);
        String expectedMessage = getExpectedMessage(city,AU);
        assertEquals(expectedMessage, message);
    }

    @Test(expected = WrongInputException.class)
    public void greetCityNull() {
        String message = greeter.createGreet(null,GMT);
    }

    @Test(expected = WrongInputException.class)
    public void greetTimezoneNull() {
        String message = greeter.createGreet(city,null);
    }

    @Test(expected = WrongInputException.class)
    public void greetCityEmpty() {
        String message = greeter.createGreet("",GMT);
    }

    private String getExpectedMessage(String city, ZoneId id) {
        int hour = ZonedDateTime.now(id).getHour();

        String resultStr;
        ResourceBundle localRes;

        if (Locale.getDefault().getCountry() == "RU") {
            localRes = ResourceBundle.getBundle(BUNDLE_NAME_RU);
        } else {
            localRes = ResourceBundle.getBundle(BUNDLE_NAME_EN);
        }

        if (hour >= 6 && hour < 9)
            resultStr = localRes.getString("msgGreeting.morning");
        else if (hour >= 9 && hour < 19)
            resultStr = localRes.getString("msgGreeting.day");
        else if (hour >= 19 && hour < 23)
            resultStr = localRes.getString("msgGreeting.evening");
        else
            resultStr = localRes.getString("msgGreeting.night");

        return resultStr + " " + city;
    }


}
