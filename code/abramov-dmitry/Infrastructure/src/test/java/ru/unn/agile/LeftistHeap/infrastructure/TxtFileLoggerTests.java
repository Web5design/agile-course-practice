package ru.unn.agile.LeftistHeap.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LeftistHeap.infrastrucure.LoggerException;
import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TxtFileLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests.log";
    private TxtFileLogger logger;

    @Before
    public void setUp() {
        try {
            logger = new TxtFileLogger(FILENAME);
        } catch (LoggerException exception) {
            fail("Logger initialization failed: " + exception.getMessage());
        }
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " was not found");
        }
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
        String[] messages = {"message 1", "message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(actualMessages.get(i).matches(".*" + messages[i] + "$"));
        }
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
