package ru.unn.agile.Huffman.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogg implements ILogg {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> takeLog() {
        return log;
    }
}
