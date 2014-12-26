package ru.unn.agile.CreditCalculator.infrastructure;

import ru.unn.agile.CreditCalculator.ViewModel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LogWriter implements ILogger {

    private final BufferedWriter writer;
    private final String filename;
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sDateFormat.format(calendar.getTime());
    }

    public LogWriter(final String filename) {
        this.filename = filename;

        BufferedWriter logWr = null;
        try {
            logWr = new BufferedWriter(new FileWriter(filename));
        } catch (Exception except) {
            except.printStackTrace();
        }
        writer = logWr;
    }

    @Override
    public List<String> getLog() {
        BufferedReader buffReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            buffReader = new BufferedReader(new FileReader(filename));
            String fileReadLine = buffReader.readLine();

            while (fileReadLine != null) {
                log.add(fileReadLine);
                fileReadLine = buffReader.readLine();
            }
        } catch (Exception except) {
            System.out.println(except.getMessage());
        }

        return log;
    }

    @Override
    public void log(final String str) {
        try {
            writer.write(now() + " > " + str);
            writer.newLine();
            writer.flush();
        } catch (Exception except) {
            System.out.println(except.getMessage());
        }
    }
}
