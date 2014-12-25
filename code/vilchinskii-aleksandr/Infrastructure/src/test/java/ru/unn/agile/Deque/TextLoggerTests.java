package ru.unn.agile.Deque;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Deque.viewmodel.ILogger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

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

        assertTrue(logger.getLog().get(0).matches(".*" + expectedMessage + "$"));
    }

    @Test
    public void canLogInfoMessage() {
        String expectedMessage = ILogger.Level.INFO + "test";

        logger.log(ILogger.Level.INFO, "test");

        assertTrue(logger.getLog().get(0).matches(".*" + expectedMessage + "$"));
    }

    @Test
    public void canLogDebugMessage() {
        String expectedMessage = ILogger.Level.DEBUG + "test";

        logger.log(ILogger.Level.DEBUG, "test");

        assertTrue(logger.getLog().get(0).matches(".*" + expectedMessage + "$"));
    }

    @Test
    public void canLogWithDate() {
        logger.log(ILogger.Level.ERROR, "test");

        assertTrue(logger.getLog().get(0).matches("^\\d{4}-\\d{2}-\\d{2} \\[\\d{2}:\\d{2}:\\d{2}\\]> .*$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"test1", "test2"};

        logger.log(ILogger.Level.ERROR, messages[0]);
        logger.log(ILogger.Level.INFO,  messages[1]);
        List<String> loggedMessages = logger.getLog();

        assertTrue(loggedMessages.get(0).matches(".*" + messages[0] + "$"));
        assertTrue(loggedMessages.get(1).matches(".*" + messages[1] + "$"));
    }
}
