import datahandlers.DataHandler;
import datahandlers.Greeter;
import writers.ConsoleWriter;
import writers.Writer;

import java.time.ZoneId;

public class Main {

    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler(args);
        String city     = dataHandler.getCity();
        ZoneId timezone = dataHandler.getTimezone();

        Greeter greeter = new Greeter();
        String message  = greeter.createGreet(city,timezone);

        Writer writer = new ConsoleWriter();
        writer.write(message);
    }
}
