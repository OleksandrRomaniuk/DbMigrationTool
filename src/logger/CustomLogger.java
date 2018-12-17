package logger;

import java.io.IOException;
import java.util.logging.*;

public class CustomLogger {

    private static final LogManager logManager = LogManager.getLogManager();


    public void setup() throws IOException {

        FileHandler fileHandler = new FileHandler("./MyLogger.log", 1000000000, 1, true);

        Formatter formater = new CustomLogerFormater();
        fileHandler.setFormatter(formater);
        Filter filter = new CustomLoggerFilter();
        fileHandler.setFilter(filter);

        logManager.reset();

        Logger rootLogger = logManager.getLogger("");

        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        rootLogger.addHandler(fileHandler);
    }
}
