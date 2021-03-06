package ru.unn.agile.Huffman.infrastructure;

import ru.unn.agile.Huffman.viewmodel.ILogg;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Logg implements ILogg {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String filename;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public Logg(final String nameFile) {
        this.filename = nameFile;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(nameFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.logWriter = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            logWriter.write(now() + " > " + s);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> takeLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(filename));
            String logLine = logReader.readLine();
            while (logLine != null) {
                log.add(logLine);
                logLine = logReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return log;
    }
}
