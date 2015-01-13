package ru.unn.agile.LeftistHeap.infrastrucure;

import ru.unn.agile.LeftistHeap.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtFileLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String fileName;

    private static String getCurrentDateTimeAsString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

    public TxtFileLogger(final String fileName) {
        this.fileName = fileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write(getCurrentDateTimeAsString() + " > " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }
}
