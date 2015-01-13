package ru.unn.agile.ComplexProcent.Infrastructure;


import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static ru.unn.agile.ComplexProcent.Infrastructure.RegularExpMatcher.matchesPattern;


public class TxtLoggerTests {
    private static final String FILENAME = "TxtLogger_Tests-ComplexPercent.log";
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLog() throws FileNotFoundException {
            new BufferedReader(new FileReader(FILENAME));
    }

    @Test
    public void canWriteMessageIntoLog() {
        String testMessage = "Ho Ho HO";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogs() {
        String firstLogMessage = "Merry";
        String secondLogMessage = "Christmas";

        logger.log(firstLogMessage);
        logger.log(secondLogMessage);
        List<String> actualMessages = logger.getLog();

        assertThat(actualMessages.get(0), matchesPattern(".*" + firstLogMessage + "$"));
        assertThat(actualMessages.get(1), matchesPattern(".*" + secondLogMessage + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "What time is it?";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
