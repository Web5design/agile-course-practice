package ru.unn.agile.QuadraticEquation.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> listLogger = new ArrayList<>();

    @Override
    public void log(final String s) {
        listLogger.add(s);
    }

    @Override
    public List<String> getLog() {
        return listLogger;
    }
}
