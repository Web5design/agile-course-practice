package ru.unn.agile.CalculateVolume.infrastructure;

import ru.unn.agile.CalculateVolume.viewmodel.ILogger;
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
    private static final String FORMAT_DATE_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufferWriter;
    private final String fileName;
    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
    public TxtLogger(final String filename) {
        this.fileName = filename;
        BufferedWriter loggerWriter = null;
        try {
            loggerWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bufferWriter = loggerWriter;
    }
    @Override
    public void log(final String s) {
        try {
            bufferWriter.write(now() + " > " + s);
            bufferWriter.newLine();
            bufferWriter.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public List<String> getLog() {
        BufferedReader bufferReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferReader = new BufferedReader(new FileReader(fileName));
            String line = bufferReader.readLine();
            while (line != null) {
                log.add(line);
                line = bufferReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return log;
    }
}
