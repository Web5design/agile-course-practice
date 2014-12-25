package ru.unn.agile.Deque;

import ru.unn.agile.Deque.viewmodel.ILogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextLogger implements ILogger{
    private final BufferedWriter writer;
    private final String filename;

    public TextLogger(final String filename) {
        this.filename = filename;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.writer = writer;
    }

    @Override
    public void log(Level level, String s) {
        s = level + s;
        try {
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        List<String> log = new ArrayList<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return null;
        }

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                log.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return log;
    }
}
