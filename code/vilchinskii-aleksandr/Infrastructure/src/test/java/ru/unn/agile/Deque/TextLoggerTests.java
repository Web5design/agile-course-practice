package ru.unn.agile.Deque;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Deque.viewmodel.ILogger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static org.junit.Assert.*;

public class TextLoggerTests {
    private ILogger logger;
    private final String logFilename = "loggerTests.txt";

    @Before
    public void setUp() {
        logger = new TextLogger(logFilename);
    }

    @Test
    public void isLogFileCreatedInitially() throws FileNotFoundException {
        new FileReader(logFilename);
    }

    @Test
    public void canLogErrorMessage() {
        String expectedMessage = ILogger.Level.ERROR + "test";

        logger.log(ILogger.Level.ERROR, "test");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }

    @Test
    public void canLogInfoMessage() {
        String expectedMessage = ILogger.Level.INFO + "test";

        logger.log(ILogger.Level.INFO, "test");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }

    @Test
    public void canLogDebugMessage() {
        String expectedMessage = ILogger.Level.DEBUG + "test";

        logger.log(ILogger.Level.DEBUG, "test");

        assertEquals(logger.getLog().get(0), expectedMessage);
    }
}
