package ru.unn.agile.CreditCalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.CreditCalculator.ViewModel.RegexMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests-Zolotov-Andrey.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canWriteLogMessage() {
        String testReport = "Test message";

        txtLogger.log(testReport);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testReport + "$"));
    }
    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] testReports = {"Test message 1", "Test message 2"};

        txtLogger.log(testReports[0]);
        txtLogger.log(testReports[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + testReports[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
