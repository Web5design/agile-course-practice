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

    public Logg(final String filename) {
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
    public void log(final String s) {
        try {
            logWriter.write(now() + " > " + s);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(filename));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
