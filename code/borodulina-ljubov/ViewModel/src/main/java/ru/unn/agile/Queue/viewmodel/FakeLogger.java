package ru.unn.agile.Queue.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void log(Level level, final String s) {
        log.add(level + s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
