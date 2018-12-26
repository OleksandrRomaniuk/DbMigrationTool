package logger;

import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomLogerFormaterTest {

    @Test
    public void messageGotFromLoggerFormaterShouldBeginFromSevereAndEndWithTestMessage() {
        LogRecord record = mock(LogRecord.class);
        when(record.getLevel()).thenReturn(Level.SEVERE);
        when(record.getSourceClassName()).thenReturn("testClass.test");
        when(record.getSourceMethodName()).thenReturn("tetsTest");
        when( record.getMessage()).thenReturn("testMessage");
        CustomLogerFormater customLogerFormater = new CustomLogerFormater();
        String test = customLogerFormater.format(record);
        System.out.println(test);
        Assert.assertEquals(test.trim().endsWith("testMessage"), true);
        Assert.assertEquals(test.trim().startsWith("SEVERE"), true);
    }

}