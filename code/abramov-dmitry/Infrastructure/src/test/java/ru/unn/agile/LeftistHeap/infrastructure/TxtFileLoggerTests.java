package ru.unn.agile.LeftistHeap.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class TxtFileLoggerTests {
    private static final String FILENAME = "./TxtFileLogger_Tests.log";
    private TxtFileLogger logger;

    @Before
    public void setUp() {
        logger = new TxtFileLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFile() {
        File logFile = new File(FILENAME);

        assertTrue(logFile.exists());
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        logger.log(testMessage);

        assertEquals(1, logger.getLog().size());
        assertTrue(logger.getLog().get(0).matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        logger.log("message 1");
        logger.log("message 2");

        List<String> actualMessages = logger.getLog();

        assertEquals(2, actualMessages.size());
        assertTrue(actualMessages.get(0).matches(".*message 1"));
        assertTrue(actualMessages.get(1).matches(".*message 2"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        logger.log(testMessage);

        assertEquals(1, logger.getLog().size());
        assertTrue(logger.getLog().get(0).matches(
                "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
