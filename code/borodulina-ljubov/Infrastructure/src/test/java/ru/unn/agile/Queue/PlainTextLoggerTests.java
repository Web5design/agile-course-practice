package ru.unn.agile.Queue;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Queue.viewmodel.ILogger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

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
        String message = ILogger.Level.ERR + "any_text";

        logger.log(ILogger.Level.ERR, "any_text");

        assertTrue(logger.getLog().get(0).matches(".*" + message + "$"));
    }

    @Test
    public void canLogInfoMessage() {
        String message = ILogger.Level.INFO + "any_text";

        logger.log(ILogger.Level.INFO, "any_text");

        assertTrue(logger.getLog().get(0).matches(".*" + message + "$"));
    }

    @Test
    public void canLogDbgMessage() {
        String message = ILogger.Level.DBG + "any_text";

        logger.log(ILogger.Level.DBG, "any_text");

        assertTrue(logger.getLog().get(0).matches(".*" + message + "$"));
    }

    @Test
    public void canLogWithDate() {
        logger.log(ILogger.Level.ERR, "any_text");

        assertTrue(logger.getLog().get(0).matches("^\\d{4}-\\d{2}-\\d{2} \\[\\d{2}:\\d{2}:\\d{2}\\]: .*$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"any_text1", "any_text2"};

        logger.log(ILogger.Level.ERR, messages[0]);
        logger.log(ILogger.Level.DBG,  messages[1]);
        List<String> loggedMessages = logger.getLog();

        assertTrue(loggedMessages.get(0).matches(".*" + messages[0] + "$"));
        assertTrue(loggedMessages.get(1).matches(".*" + messages[1] + "$"));
    }
}
