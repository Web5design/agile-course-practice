package ru.unn.agile.Fraction.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String logMessage) {
        log.add(logMessage);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
