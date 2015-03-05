package ru.unn.agile.Fraction.infrastructure;

import ru.unn.agile.Fraction.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TextLogger implements ILogger {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufWriter;
    private final String fileName;

    public TextLogger(final String fileName) {
        this.fileName = fileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufWriter = logWriter;
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    @Override
    public void log(final String s) {
        try {
            bufWriter.write(now() + "::" + s + "\n");
            bufWriter.flush();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader fileReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            String line = fileReader.readLine();

            while (line != null) {
                log.add(line);
                line = fileReader.readLine();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return log;
    }
}
