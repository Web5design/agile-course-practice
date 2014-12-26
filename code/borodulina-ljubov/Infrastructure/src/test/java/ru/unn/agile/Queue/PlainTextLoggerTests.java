package ru.unn.agile.Queue;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Queue.viewmodel.ILogger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static org.junit.Assert.*;

public class PlainTextLoggerTests {
    private ILogger logger;
    private final String logFilename = "loggerTests.log";

    @Before
    public void setUp() {
        logger = new PlainTextLogger(logFilename);
    }

    @Test
    public void isLogFileCreatedInitially() throws FileNotFoundException {
        new FileReader(logFilename);
    }

    @Test
    public void canLogErrMessage() {
        String expectedMessage = ILogger.Level.ERR + "any_text";

        logger.log(ILogger.Level.ERR, "any_text");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }

    @Test
    public void canLogInfoMessage() {
        String expectedMessage = ILogger.Level.INFO + "any_text";

        logger.log(ILogger.Level.INFO, "any_text");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }

    @Test
    public void canLogDbgMessage() {
        String expectedMessage = ILogger.Level.DBG + "any_text";

        logger.log(ILogger.Level.DBG, "any_text");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }
}
