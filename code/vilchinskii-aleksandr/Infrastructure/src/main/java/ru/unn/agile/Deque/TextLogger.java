package ru.unn.agile.Deque;

import ru.unn.agile.Deque.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TextLogger implements ILogger{
    private final BufferedWriter writer;
    private final String filename;
    private static final String DATETIME = "yyyy-MM-dd [HH:mm:ss]> ";

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
    public void log(Level level, String msg) {
        try {
            writer.write(now() + level + msg + "\n");
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

    private String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat(DATETIME, Locale.ENGLISH);
        return date.format(calendar.getTime());
    }
}
