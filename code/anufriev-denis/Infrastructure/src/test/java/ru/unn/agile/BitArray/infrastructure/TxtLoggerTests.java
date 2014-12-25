package ru.unn.agile.BitArray.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
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
    public void canCreateFileForLog() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " was not found!");
        }
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
    public void canWriteFewLogMessages() {
        String[] textMessages = {"Text message 1", "Text message 2"};
        txtLogger.log(textMessages[0]);
        txtLogger.log(textMessages[1]);

        List<String> lastMessages = txtLogger.getLog();

        for (int i = 0; i < lastMessages.size(); i++) {
            assertThat(lastMessages.get(i), matchesPattern(".*" + textMessages[i] + "$"));
        }
    }
}
