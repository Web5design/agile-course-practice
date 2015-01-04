package ru.unn.agile.CreditCalculator.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String str) {
        log.add(str);
    }

}
