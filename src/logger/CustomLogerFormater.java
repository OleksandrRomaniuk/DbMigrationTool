package logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A custom logger which defines a format of messages written by loggers.
 */
public class CustomLogerFormater extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(record.getMillis());
        return record.getLevel() + " "
            + logTime.format(cal.getTime())
            + " || "
            + record.getSourceClassName().substring(
            record.getSourceClassName().lastIndexOf(".") + 1,
            record.getSourceClassName().length())
            + "."
            + record.getSourceMethodName()
            + "() : "
            + record.getMessage() + "\n";
    }
}
