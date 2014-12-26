package ru.unn.agile.Queue;

import ru.unn.agile.Queue.viewmodel.ILogger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlainTextLogger implements ILogger{
    private final String filename;
    private final BufferedWriter writer;


    public PlainTextLogger(final String filename) {
        this.filename = filename;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.filename));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.writer = writer;
    }

    @Override
    public void log(Level level, String s) {
        s = level + s;
        try {
            writer.write(s);
            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        List<String> log = new ArrayList<>();
        String line;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return null;
        }

        try {
            while ((line = reader.readLine()) != null) {
                log.add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return log;
    }
}
