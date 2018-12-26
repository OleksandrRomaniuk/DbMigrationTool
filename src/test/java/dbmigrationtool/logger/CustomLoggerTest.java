package dbmigrationtool.logger;

import logger.CustomLogger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.LogManager;

import static org.junit.Assert.*;

public class CustomLoggerTest {
    private static final LogManager logManager = LogManager.getLogManager();
    @Test
    public void shouldHaveOnlyOneFileHandlerAndLevelAtINFO() throws IOException {
        CustomLogger customLogger = new CustomLogger();
        customLogger.setup();
        Assert.assertEquals(logManager.getLogger("").getHandlers().length, 1);
        Assert.assertEquals(logManager.getLogger("").getHandlers()[0].getClass().getName(), "java.util.logging.FileHandler");
        Assert.assertEquals(logManager.getLogger("").getLevel().getName(), "INFO");

    }
}