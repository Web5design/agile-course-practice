package ru.unn.agile.QuadraticEquation.infrastructure;

import ru.unn.agile.QuadraticEquation.viewmodel.ILogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_TIME_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writerLog;
    private final String file;

    private static String now() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(date.getTime());
    }

    public TxtLogger(final String filename) {
        this.file = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerLog = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writerLog.write(now() + " > " + s);
            writerLog.newLine();
            writerLog.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader readerLog;
        ArrayList<String> log = new ArrayList<String>();
        try {
            readerLog = new BufferedReader(new FileReader(file));
            String line = readerLog.readLine();

            while (line != null) {
                log.add(line);
                line = readerLog.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
