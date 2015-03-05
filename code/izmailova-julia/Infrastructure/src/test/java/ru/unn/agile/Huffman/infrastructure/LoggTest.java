package ru.unn.agile.Huffman.infrastructure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import  ru.unn.agile.Huffman.viewmodel.Regex;


public class LoggTest {
    private static final String FILENAME = "./TxtLoggerTests-lab3.log";
    private Logg logg;

    @Before
    public void setUp() {
        logg = new Logg(FILENAME);
    }

    @Test
    public void canCreateLoggWithFileName() {
        assertNotNull(logg);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteInLogMessage() {
        String testMessage = "Test message";

        logg.log(testMessage);

        String message = logg.getLog().get(0);
        assertThat(message, Regex.pattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSomeLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        logg.log(messages[0]);
        logg.log(messages[1]);

        List<String> actualMessages = logg.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), Regex.pattern(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogHaveDateAndTime() {
        String testMessage = "Test message";

        logg.log(testMessage);

        String message = logg.getLog().get(0);
        assertThat(message, Regex.pattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
