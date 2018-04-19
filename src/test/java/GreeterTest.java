import datahandlers.Greeter;
import exceptions.WrongInputException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

import static org.junit.Assert.*;

public class GreeterTest {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Greeter greeter = new Greeter();


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

        if (Locale.getDefault().getCountry() == "RU") {
            if (hour >= 6 && hour < 9)
                resultStr = "Доброе утро, ";
            else if (hour >= 9 && hour < 19)
                resultStr = "Добрый день, ";
            else if (hour >= 19 && hour < 23)
                resultStr = "Добрый вечер, ";
            else
                resultStr = "Спокойной ночи, ";
        } else {
            if (hour >= 6 && hour < 9)
                resultStr = "Good morning, ";
            else if (hour >= 9 && hour < 19)
                resultStr = "Good day, ";
            else if (hour >= 19 && hour < 23)
                resultStr = "Good evening, ";
            else
                resultStr = "Good night, ";
        }

        return resultStr + city;
    }


}