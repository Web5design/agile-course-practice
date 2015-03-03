package ru.unn.agile.calculateSalary.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements UniversalLogger {
    private ArrayList<String> textInLog = new ArrayList<String>();

    @Override
    public void textInLog(final String message) {
        textInLog.add(message);
    }

    @Override
    public List<String> getLog() {
        return textInLog;
    }
}
