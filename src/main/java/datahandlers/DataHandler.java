package datahandlers;

import exceptions.WrongInputException;
import org.apache.log4j.*;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class DataHandler {
    String city;
    String timezone;

    private final static String BUNDLE_NAME = "data";
    private final static ResourceBundle res = ResourceBundle.getBundle(BUNDLE_NAME);

    private final static Logger logger = Logger.getLogger(DataHandler.class);

    public DataHandler(String[] args) {
        handle(args);
    }

    private void handle(String[] args) {
        //это основной метод для обработки данных. его алгоритм описан на гитхабе в README

        int length = args.length;

        logger.info("Input line: " + Arrays.toString(args));
        String message;

        if (length > 1) {
            timezone = getTimeZoneFromInput(args[length-1]);

            if (timezone == null) {
                city = getCityFromInput(args,length);
                if (!checkCityName(city)) {
                    message = res.getString("msgError.wrongSymbolInName1");
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
                message = res.getString("msgError.emptyCityParam");
                logger.error(message);
                throw new WrongInputException(message);
            }

            city = getCityFromInput(args,length);
            timezone = getTimeZoneFromCity(city);
        } else {
            message = res.getString("msgError.emptyCityParam");
            logger.error(message);
            throw new WrongInputException(message);
        }

        if (!checkCityName(city)) {
            message = res.getString("msgError.wrongSymbolInName2");
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

    private boolean checkCityName(String city) { //проверка валидности названия города
        if (city.matches("^[ A-Za-z]+$"))
            return true;
        return false;
    }

    private String getCityFromInput(String[] arr, int length) { //компонуем город из входного массива
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append((arr[i]).trim()).append(" ");

        return sb.toString();
    }

    private String getTimeZoneFromInput(String id) { //пытаемся получить таймзону из последнего аргумента входных данных
        String newId = id.trim().replaceAll("[\\s]+", "_");

        String timezone = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.equals(newId))
                .findFirst()
                .orElse(null);
        return timezone;
    }

    private String getTimeZoneFromCity(String id) { //пытаемся получить таймзону из названия города
        String newId = id.trim().replaceAll("[\\s]+", "_");

        String timezone = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.contains(newId))
                .findFirst()
                .orElse("GMT");
        return timezone;
    }
}
