package ru.unn.agile.QuadraticEquation.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.QuadraticEquation.infrastructure.RegularExpressionMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String LOGGER_FILE_NAME = "./TxtLoggerTests.log";
    private TxtLogger textLogger;

    @Before
    public void setUp() {
        textLogger = new TxtLogger(LOGGER_FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(textLogger);
    }

    @Test
    public void checkLoggerFileWasCreated() throws FileNotFoundException {
        new FileReader(LOGGER_FILE_NAME);
    }

    @Test
    public void canWriteMessageInLog() {
        String message = "Test message";

        textLogger.log(message);

        String logMessage = textLogger.getLog().get(0);
        assertThat(logMessage, matchesPattern(".*" + message + "$"));
    }

    @Test
    public void canWriteSeveralMessageInLog() {
        String[] messages = {"Test message №1", "Test message №2"};

        textLogger.log(messages[0]);
        textLogger.log(messages[1]);

        List<String> actualMessages = textLogger.getLog();
        assertThat(actualMessages.get(0), matchesPattern(".*" + messages[0] + "$"));
        assertThat(actualMessages.get(1), matchesPattern(".*" + messages[1] + "$"));

    }

    @Test
    public void doesLoggerContainDateAndTime() {
        String message = "Test message";

        textLogger.log(message);

        String logMessage = textLogger.getLog().get(0);
        assertThat(logMessage, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
