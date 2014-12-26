package ru.unn.agile.calculateSalary.Infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import ru.unn.agile.calculateSalary.ViewModel.RegexMatcher;

public class RealLoggerTests {
    private static final String FILENAME = "./RealLoggerTests-lazarev-lab3.log";
    private RealLogger realLogger;

    @Before
    public void setUp() {
        realLogger = new RealLogger(FILENAME);
    }

    @Test
    public void createLoggerWithFilename() {
        assertNotNull(realLogger);
    }

    @Test
    public void createLogFileOnHDD() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File" + FILENAME + " wasn't found.");
        }
    }

    @Test
    public void createLogMessage() {
        String example = "I'm test.";

        realLogger.textInLog(example);
        String message = realLogger.getLog().get(0);
        assertThat(message, RegexMatcher.matchesPattern(".*" + example + "$"));
    }

    @Test
    public void createMoreThanOneLogLine() {
        String[] fewMessages = {"I'm first test", "I'm second test"};

        realLogger.textInLog(fewMessages[0]);
        realLogger.textInLog(fewMessages[1]);

        List<String> messagesInLog = realLogger.getLog();
        for (int i = 0; i < messagesInLog.size(); i++) {
            assertThat(messagesInLog.get(i),
                       RegexMatcher.matchesPattern(".*" + fewMessages[i] + "$"));
        }
    }

    @Test
    public void logIncludeDateAndTime() {
        String example = "I'm test";

        realLogger.textInLog(example);
        String message = realLogger.getLog().get(0);
        assertThat(message,
                   RegexMatcher.matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
