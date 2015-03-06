package ru.unn.agile.Fraction.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextLoggerTests {
    private static final String FILE = "./TextLoggerTests.log";
    private TextLogger textLogger;

    @Before
    public void setUp() {
        textLogger = new TextLogger(FILE);
    }

    @Test
    public void ableToCreateLoggerWithGivenFile() {
        assertNotNull(textLogger);
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test log message";
        textLogger.log(testMessage);

        String message = textLogger.getLog().get(0);
        assertThat(message, havePatternMatch(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2", "Test message 3"};
        textLogger.log(messages[0]);
        textLogger.log(messages[1]);
        textLogger.log(messages[2]);

        List<String> actualMessages = textLogger.getLog();
        assertThat(actualMessages.get(0), havePatternMatch(".*" + messages[0] + "$"));
        assertThat(actualMessages.get(1), havePatternMatch(".*" + messages[1] + "$"));
        assertThat(actualMessages.get(2), havePatternMatch(".*" + messages[2] + "$"));
    }

    @Test
    public void containsDateAndTime() {
        String testMessage = "Test message";
        textLogger.log(testMessage);

        String message = textLogger.getLog().get(0);
        assertThat(message, havePatternMatch("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}::.*"));
    }

    private class LogMatcher extends BaseMatcher {
        private final String regexp;

        public LogMatcher(final String expression) {
            this.regexp = expression;
        }

        public void describeTo(final Description descr) {
            descr.appendText("have match with regex = ");
            descr.appendText(regexp);
        }

        public boolean matches(final Object obj) {
            String convObj = (String) obj;
            return convObj.matches(regexp);
        }
    }

    private Matcher<? super String> havePatternMatch(final String expression) {
        LogMatcher matcher = new LogMatcher(expression);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) matcher;
        return castedMatcher;
    }


}
