package logger;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * A class which defines a level below which logger messages are not processed by loggers.
 */
public class CustomLoggerFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {

        return record.getLevel().intValue() >= Level.INFO.intValue();
    }
}

