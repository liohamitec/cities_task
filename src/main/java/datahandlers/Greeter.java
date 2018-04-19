package datahandlers;

import exceptions.WrongInputException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Greeter {

    final static Logger logger = Logger.getLogger(DataHandler.class);

    public String createGreet(String city, ZoneId id) {
        String msg;
        if (city == null || id == null) {
            msg = "Ни один из параметров не может быть null!";
            logger.error(msg);
            throw new WrongInputException(msg);
        } else if (city.isEmpty()) {
            msg = "Параметр city не может быть пустым!";
            logger.error(msg);
            throw new WrongInputException(msg);
        }

        int curHour = ZonedDateTime.now(id).getHour();
        logger.info("Час текущей таймзоны: " + curHour);

        String locale = Locale.getDefault().getCountry();
        String pathToLocale = getResourceByCountryCode(locale);
        int timeCode = getTimeCode(curHour);

        logger.info("Локаль: " + locale);

        String greetingStr = createGreeting(pathToLocale,timeCode);
        greetingStr += city;

        logger.info("Greeter class returned string: " + greetingStr);

        return greetingStr;
    }

    private int getTimeCode(int hour) {
        if (hour >= 6 && hour < 9)
            return 1;
        if (hour >= 9 && hour < 19)
            return 2;
        if (hour >= 19 && hour < 23)
            return 3;

        return 4;
    }

    private String getResourceByCountryCode(String cc) {
        if (cc.equals("RU")) {
            return "greetings_ru";
        } else {
            return "greetings_en";
        }
    }

    private String createGreeting(String pathToLocale, int greetingCode) {
        StringBuilder sb = new StringBuilder();

        try (Scanner in = new Scanner(new File("./"+pathToLocale))) {
            boolean stopFlag = false;
            String[] tokens;
            while (in.hasNextLine() && !stopFlag) {
                tokens = in.nextLine().split(" ");
                int code = Integer.parseInt(tokens[0]);
                if (code == greetingCode) {
                    stopFlag = true;
                    for (int i = 1; i < tokens.length; i++)
                        sb.append(tokens[i]).append(" ");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return sb.toString();
        }
    }
}
