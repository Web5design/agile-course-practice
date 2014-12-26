package ru.unn.agile.CreditCalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.CreditCalculator.ViewModel.RegexMatcher.matchesPattern;

public class LogWriterTests {
    private static final String FILENAME = "./TxtLoggerTests-Zolotov-Andrey.log";
    private LogWriter logWriter;

    @Before
    public void setUp() {
        logWriter = new LogWriter(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logWriter);
    }

    @Test
    public void canWriteLogMessage() {
        String testReport = "Test message";

        logWriter.log(testReport);

        String message = logWriter.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testReport + "$"));
    }

    @Test
    public void canCreateLogFileOnDisk() throws FileNotFoundException {
            new BufferedReader(new FileReader(FILENAME));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] testReports = {"Test message 1", "Test message 2"};

        logWriter.log(testReports[0]);
        logWriter.log(testReports[1]);

        List<String> actualMessages = logWriter.getLog();
        assertThat(actualMessages.get(0), matchesPattern(".*" + testReports[0] + "$"));
        assertThat(actualMessages.get(1), matchesPattern(".*" + testReports[1] + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        logWriter.log(testMessage);

        String message = logWriter.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
