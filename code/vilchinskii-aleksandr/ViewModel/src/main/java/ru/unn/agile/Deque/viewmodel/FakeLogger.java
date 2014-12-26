package ru.unn.agile.Deque.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<String>();

    @Override
    public void log(final Level level, final String msg) {
        log.add(level + msg);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
