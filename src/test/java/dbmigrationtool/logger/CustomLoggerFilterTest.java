package logger;

import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomLoggerFilterTest {

    @Test
    public void shouldReturnTrueForSevereLevel() {
        LogRecord record = mock(LogRecord.class);
        when(record.getLevel()).thenReturn(Level.SEVERE);
        CustomLoggerFilter customLoggerFilter = new CustomLoggerFilter();
        Assert.assertEquals(customLoggerFilter.isLoggable(record), true);
    }
}