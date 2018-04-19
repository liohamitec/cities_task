package datahandlers;

import exceptions.WrongInputException;
import org.apache.log4j.*;

import java.time.ZoneId;
import java.util.Arrays;

public class DataHandler {
    String city;
    String timezone;


    final static Logger logger = Logger.getLogger(DataHandler.class);

    public DataHandler(String[] args) {
        handle(args);
    }

    private void handle(String[] args) {
        int length = args.length;

        logger.info("Input line: " + Arrays.toString(args));
        String message;

        if (length > 1) {
            timezone = getTimeZoneFromInput(args[length-1]);

            if (timezone == null) {
                city = getCityFromInput(args,length);
                if (!checkCityName(city)) {
                    message = "Последий параметр не является идентификатором. " +
                                "Доп символы кроме пробелов и букв в названии города недопустимы: " +
                                city +
                                "\nПрограмма завершена.";
                    logger.error(message);
                    throw new WrongInputException(message);
                }
                timezone = getTimeZoneFromCity(city);
            } else {
                city = getCityFromInput(args,length-1);
            }
        }
        else if (args.length == 1) {
            if (args[0].isEmpty()) {
                message = "Параметр city не может быть пустым! Программа завершена.";
                logger.error(message);
                throw new WrongInputException(message);
            }

            city = getCityFromInput(args,length);
            timezone = getTimeZoneFromCity(city);
        } else {
            message = "Город не указан! Программа завершена.";
            logger.error(message);
            throw new WrongInputException(message);
        }

        if (!checkCityName(city)) {
            message = "Доп символы кроме пробелов и букв в названии города недопустимы: " +
                    city + "\nПрограмма завершена.";
            logger.error(message);
            throw new WrongInputException(message);
        }

        logger.info("city(DataHandle): " + getCity() + ", timezone(DataHandle): " + timezone);

    }

    public String getCity() {
        return city;
    }

    public ZoneId getTimezone() {
        ZoneId id = ZoneId.of(timezone);
        return id;
    }

    private boolean checkCityName(String city) {
        if (city.matches("^[ A-Za-z]+$"))
            return true;
        return false;
    }

    private String getCityFromInput(String[] arr, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append((arr[i]).trim()).append(" ");

        return sb.toString();
    }

    private String getTimeZoneFromInput(String id) {
        String newId = id.trim().replaceAll("[\\s]+", "_");

        String timezone = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.equals(newId))
                .findFirst()
                .orElse(null);
        return timezone;
    }

    private String getTimeZoneFromCity(String id) {
        String newId = id.trim().replaceAll("[\\s]+", "_");

        String timezone = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.contains(newId))
                .findFirst()
                .orElse("GMT");
        return timezone;
    }
}
