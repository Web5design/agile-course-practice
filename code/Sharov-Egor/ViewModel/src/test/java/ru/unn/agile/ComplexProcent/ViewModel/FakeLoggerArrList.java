package ru.unn.agile.ComplexProcent.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLoggerArrList implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String s) {
        log.add(s);
    }
}
