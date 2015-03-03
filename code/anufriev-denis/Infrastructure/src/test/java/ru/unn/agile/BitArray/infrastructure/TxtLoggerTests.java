package ru.unn.agile.BitArray.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.BitArray.viewModel.RegexMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests-lab3.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateFileForLog() throws FileNotFoundException {
        new BufferedReader(new FileReader(FILENAME));
    }

    @Test
    public void canWriteMessage() {
        String testMessage = "Some test message";
        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);

        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Some test message";
        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);

        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteFewLogMessage() {
        String[] testMessages = {"Message One", "Message Two", "Message Three"};

        txtLogger.log(testMessages[0]);
        txtLogger.log(testMessages[1]);
        txtLogger.log(testMessages[2]);

        List<String> lastMessages = txtLogger.getLog();
        assertThat(lastMessages.get(0), matchesPattern(".*" + testMessages[0] + "$"));
        assertThat(lastMessages.get(1), matchesPattern(".*" + testMessages[1] + "$"));
        assertThat(lastMessages.get(2), matchesPattern(".*" + testMessages[2] + "$"));
    }
}
