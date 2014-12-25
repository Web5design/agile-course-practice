package ru.unn.agile.ComplexProcent.Infrastructure;

import ru.unn.agile.ComplexProcent.ViewModel.*;

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
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.logWriter = logWriter;
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedLogReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedLogReader = new BufferedReader(new FileReader(filename));
            String line = bufferedLogReader.readLine();
            while (line != null) {
                log.add(line);
                line = bufferedLogReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

    @Override
    public void log(final String message) {
        try {
            logWriter.write(now() + " > " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}