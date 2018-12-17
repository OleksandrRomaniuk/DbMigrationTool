package logger;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomLoggerFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {

        return record.getLevel().intValue() >= Level.INFO.intValue();
    }
}
