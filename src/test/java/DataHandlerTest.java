import datahandlers.DataHandler;
import exceptions.WrongInputException;
import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.*;

public class DataHandlerTest {

    @Test(expected = RuntimeException.class)
    public void noInputData() {
        String[] args = new String[]{};
        DataHandler dataHandler = new DataHandler(args);
    }

    @Test(expected = RuntimeException.class)
    public void emptyData() {
        String[] args = new String[]{""};
        DataHandler dataHandler = new DataHandler(args);
    }

    @Test
    public void tonsOfSpacesInData() {
        String[] args = new String[]{"City     ", "     Name","          Etc/GMT+10"};
        DataHandler dataHandler = new DataHandler(args);

        String resultMsg = dataHandler.getCity();
        assertEquals( "City Name ", resultMsg);

        ZoneId timezone = dataHandler.getTimezone();
        assertEquals(ZoneId.of("Etc/GMT+10"), timezone);
    }

    @Test
    public void getTimezoneByCity() {
        String[] args = new String[]{"Costa","Rica"};
        DataHandler dataHandler = new DataHandler(args);

        ZoneId timezone = dataHandler.getTimezone();
        assertEquals(ZoneId.of("America/Costa_Rica"), timezone);
    }

    @Test
    public void setDefaultTimezone() {
        String[] args = new String[]{"Dnipro"};
        DataHandler dataHandler = new DataHandler(args);

        ZoneId timezone = dataHandler.getTimezone();
        assertEquals(ZoneId.of("GMT"), timezone);
    }

    @Test(expected = WrongInputException.class)
    public void wrongSymbolsInTimezone() {
        String[] args = new String[]{"CityOnCuba","Cuba!"};
        DataHandler dataHandler = new DataHandler(args);
    }

    @Test(expected = WrongInputException.class)
    public void wrongSymbolsInCityFullData() {
        String[] args = new String[]{"CityOnCuba_","Cuba"};
        DataHandler dataHandler = new DataHandler(args);
    }

    @Test(expected = WrongInputException.class)
    public void wrongSymbolsInCityNoTimezone() {
        String[] args = new String[]{"Kiev_"};
        DataHandler dataHandler = new DataHandler(args);
    }

    @Test
    public void correctCityDifferentTimezone() {
        String[] args = new String[]{"Dnipro","Cuba"};
        DataHandler dataHandler = new DataHandler(args);

        String resultMsg = dataHandler.getCity();
        assertEquals( "Dnipro ", resultMsg);

        ZoneId timezone = dataHandler.getTimezone();
        assertEquals(ZoneId.of("Cuba"), timezone);
    }

}