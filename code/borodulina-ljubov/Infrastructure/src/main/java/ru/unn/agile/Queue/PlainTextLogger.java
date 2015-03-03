package ru.unn.agile.Queue;

import ru.unn.agile.Queue.viewmodel.ILogger;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlainTextLogger implements ILogger {
    private final String filename;
    private final BufferedWriter writer;
    private static final String DATETIME = "yyyy-MM-dd [HH:mm:ss]: ";

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
    public void log(final Level level, final String s) {
        String msg = timestamp() + level + s + "\n";
        try {
            writer.write(msg);
            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        List<String> log = new ArrayList<String>();
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

    private String timestamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat(DATETIME, Locale.ENGLISH);
        return date.format(calendar.getTime());
    }
}
