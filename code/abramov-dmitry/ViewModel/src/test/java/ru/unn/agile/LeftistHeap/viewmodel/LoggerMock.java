package ru.unn.agile.LeftistHeap.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class LoggerMock implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String s) {
        log.add(s);
    }
}
