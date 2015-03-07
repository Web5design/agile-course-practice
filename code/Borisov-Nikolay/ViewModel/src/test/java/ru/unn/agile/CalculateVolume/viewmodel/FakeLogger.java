package ru.unn.agile.CalculateVolume.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> fakeLogMessages = new ArrayList<String>();

    @Override
    public void log(final String s) {
        fakeLogMessages.add(s);
    }

    @Override
    public List<String> getLog() {
        return fakeLogMessages;
    }
}
