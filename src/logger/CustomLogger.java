package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A class which sets up a file handler for loggers of the tool. The file filter and file formatter are set to the handler.
 */
public class CustomLogger {

    private static final LogManager logManager = LogManager.getLogManager();

    /**
     * A method which sets the file handler, formatter and filter.
     * @throws IOException is thrown at writing the log file.
     */
    public void setup() throws IOException {

        FileHandler fileHandler = new FileHandler("./MyLogger.log", 1000000000, 1, true);

        Formatter formatter = new CustomLogerFormater();
        fileHandler.setFormatter(formatter);
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
