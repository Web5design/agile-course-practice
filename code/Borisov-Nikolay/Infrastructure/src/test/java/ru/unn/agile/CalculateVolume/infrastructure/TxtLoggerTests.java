package ru.unn.agile.CalculateVolume.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static junit.framework.TestCase.assertNotNull;
import static ru.unn.agile.CalculateVolume.infrastructure.RegexConform.matchesPattern;

public class TxtLoggerTests {
    private static final String FNAME = "./TxtLogTest.log";
    private TxtLogger textLog;

    @Before
    public void setUp() {
        textLog = new TxtLogger(FNAME);
    }

    @Test
    public void canCreateLogWithFileName() {
        assertNotNull(textLog);
    }

    @Test
    public void canCreateLogFileOnDisk() throws FileNotFoundException {
        new BufferedReader(new FileReader(FNAME));
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";
        textLog.log(testMessage);
        String message = textLog.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteFewLogMessage() {
        String[] messages = {"Message 1", "Message 2"};
        textLog.log(messages[0]);
        textLog.log(messages[1]);
        List<String> curMessages = textLog.getLog();
        assertThat(curMessages.get(0), matchesPattern(".*" + messages[0] + "$"));
        assertThat(curMessages.get(1), matchesPattern(".*" + messages[1] + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";
        textLog.log(testMessage);
        String message = textLog.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
